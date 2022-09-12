package ir.payeshgaran.project1.service.implementation;

import ir.payeshgaran.project1.config.AccountDetails;
import ir.payeshgaran.project1.model.User;
import ir.payeshgaran.project1.repo.UserRepo;
import ir.payeshgaran.project1.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class AccountServiceImplementation implements AccountService, UserDetailsService {


    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }


    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }


    @Override
    public User findUserByAccountNumber(String accountNumber) throws AccountNotFound {
        return userRepo.findUserByAccountNumber(accountNumber);
    }

    @Override
    public Optional<User> findById(Long aLong) throws AccountNotFound {
        return userRepo.findById(aLong);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {



        User user = userRepo.findUserByAccountNumber(username);
        if (user == null) {
            log.info("Account not found");
            throw new UsernameNotFoundException("Account not found");
        } else {
            log.info("Account {} found", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        return new org.springframework.security.core.userdetails.User(
                user.getAccountNumber(), user.getPassword(), authorities);
    }


}

class AccountNotFound extends RuntimeException {
    public AccountNotFound(String message) {
        super(message);
    }
}
