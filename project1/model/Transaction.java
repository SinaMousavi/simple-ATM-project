package ir.payeshgaran.project1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity
@Table(name = "transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "sequence")
    @SequenceGenerator(
            name = "sequence",
            sequenceName = "sequence",
            allocationSize = 1
    )
    private Long id;
    @Column(name = "amount")
    private double amount;
    @Column(name = "depositorId")
    private Long depositorId;
    @Column(name = "receiverId")
    private Long receiverId;

    public Transaction(double amount, Long depositorId, Long receiverId) {
        this.amount = amount;
        this.depositorId = depositorId;
        this.receiverId = receiverId;
    }
}
