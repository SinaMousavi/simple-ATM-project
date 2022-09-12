package ir.payeshgaran.project1.service;

import ir.payeshgaran.project1.model.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction save(Transaction transaction, double balance);

    List<Transaction> getUserTransactions(Long userId);

    List<Transaction> findall();

}
