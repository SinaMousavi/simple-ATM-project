package ir.payeshgaran.project1;

import ir.payeshgaran.project1.model.Transaction;
import ir.payeshgaran.project1.model.User;
import ir.payeshgaran.project1.repo.TransactionRepo;
import ir.payeshgaran.project1.repo.UserRepo;
import ir.payeshgaran.project1.service.implementation.TransactionServiceImplementation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ir.payeshgaran.project1.service.implementation.AccountServiceImplementation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class Project1Application {

    public static void main(String[] args) {
        SpringApplication.run(Project1Application.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(AccountServiceImplementation accountServiceImplementation) {
        return args -> {
            User sina = new User(
                    1L, "1234");
            User javad = new User(
                    2L, "1234");
            User reza = new User(
                    3L, "1234");

            accountServiceImplementation.save(sina);
            accountServiceImplementation.save(javad);
            accountServiceImplementation.save(reza);
        };
    }

    @Bean
    CommandLineRunner run(TransactionServiceImplementation transactionServiceImplementation) {
        return args -> {
            Transaction first = new Transaction(
                    200,
                    1L,
                    2L);
            transactionServiceImplementation.save(first, 200);
        };
    }
}
