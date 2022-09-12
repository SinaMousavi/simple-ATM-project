package ir.payeshgaran.project1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "sequence")
    @SequenceGenerator(
            name = "sequence",
            sequenceName = "sequence",
            allocationSize = 1
    )
    private Long id;
    @Column(name = "accountNumber")
    private String accountNumber;
    @Column(name = "password")
    private String password;
    @Column(name = "accountBalance")
    private double accountBalance = 10000;

    @JoinColumn
    @OneToMany
    private List<Transaction> transactions = new ArrayList<>();

    public User(Long accountNumber, String password) {
        this.accountNumber = String.valueOf(accountNumber);
        this.password = password;
    }

}
//, unique = true