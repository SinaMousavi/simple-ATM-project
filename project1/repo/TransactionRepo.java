package ir.payeshgaran.project1.repo;

import ir.payeshgaran.project1.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    @Override
    Transaction save(Transaction transaction);

    @Override
    List<Transaction> findAll();

    @Query(nativeQuery = true, value = "select * from Transaction t where t.receiverId=:accountId or t.depositorId = :accountId order by t.id DESC LIMIT 10")
    List<Transaction> getUserTransactions(@Param("accountId") Long accountId);


}
