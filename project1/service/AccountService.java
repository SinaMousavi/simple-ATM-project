package ir.payeshgaran.project1.service;

import ir.payeshgaran.project1.model.User;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    User save(User user);

    List<User> findAll();

    User findUserByAccountNumber(String accountNumber);

    Optional<User> findById(Long aLong);


}
