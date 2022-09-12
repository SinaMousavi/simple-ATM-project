package ir.payeshgaran.project1.service.implementation;

import ir.payeshgaran.project1.model.User;
import ir.payeshgaran.project1.model.Transaction;
import ir.payeshgaran.project1.repo.TransactionRepo;
import ir.payeshgaran.project1.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Data
@Transactional
public class TransactionServiceImplementation implements TransactionService {
    private final TransactionRepo transactionRepo;
    private final AccountServiceImplementation accountService;


    @Override
    public Transaction save(Transaction transaction, double amount) throws AccountNotFound {
        Optional<User> depositor = accountService.findById(transaction.getDepositorId());
        Optional<User> receiver = accountService.findById(transaction.getReceiverId());
        if (depositor != null && receiver != null) {
            depositor.get().getTransactions().add(transaction);
            depositor.get().setAccountBalance(depositor.get().getAccountBalance() - amount);
            receiver.get().setAccountBalance(receiver.get().getAccountBalance() + amount);

        } else
            throw new AccountNotFound("Account Number Not Found");
        return transactionRepo.save(transaction);
    }

    @Override
    public List<Transaction> getUserTransactions(Long userId) {
        return transactionRepo.getUserTransactions(userId);
    }

    @Override
    public List<Transaction> findall() {
        return transactionRepo.findAll();
    }

}

