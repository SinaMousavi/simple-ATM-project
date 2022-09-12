package ir.payeshgaran.project1.repo;

import ir.payeshgaran.project1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User save(User user);

    @Override
    List<User> findAll();

    User findUserByAccountNumber(String accountNumber);

    @Override
    Optional<User> findById(Long aLong);


}
