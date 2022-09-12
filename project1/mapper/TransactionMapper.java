package ir.payeshgaran.project1.mapper;


import ir.payeshgaran.project1.dto.TransactionDTO;
import ir.payeshgaran.project1.model.Transaction;
import ir.payeshgaran.project1.service.implementation.AccountServiceImplementation;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;



@AllArgsConstructor
public class TransactionMapper {


    private Transaction transaction;
    private TransactionDTO transactionDTO;
    private AccountServiceImplementation accountService;



    public Transaction toEntity(TransactionDTO transactionDTO) {
        Long depositorId = accountService.findUserByAccountNumber(transactionDTO.getDepositor()).getId();
        Long receiverId = accountService.findUserByAccountNumber(transactionDTO.getReceiver()).getId();

        transaction.setAmount(transactionDTO.getAmount());
        transaction.setDepositorId(depositorId);
        transaction.setReceiverId(receiverId);

        return transaction;
    }

    public List<TransactionDTO> toDTOS(List<Transaction> transactions) {
        List<TransactionDTO> transactionDTOs = new ArrayList<>();
        for (Transaction transaction : transactions) {
            TransactionDTO transactionDTO = new TransactionDTO();

            String depositor = accountService.findById(transaction.getDepositorId()).get().getAccountNumber();
            String receiver = accountService.findById(transaction.getReceiverId()).get().getAccountNumber();

            transactionDTO.setAmount(transaction.getAmount());
            transactionDTO.setDepositor(depositor);
            transactionDTO.setReceiver(receiver);
            transactionDTOs.add(transactionDTO);

        }
        return transactionDTOs;
    }

   /* public TransactionDTO toDTO(Transaction transaction) {
        String depositor = accountService.findById(transaction.getDepositorId()).get().getAccountNumber();
        String receiver = accountService.findById(transaction.getReceiverId()).get().getAccountNumber();
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setDepositor(depositor);
        transactionDTO.setReceiver(receiver);
        return transactionDTO;
    }

    public List<Transaction> toEntities(List<TransactionDTO> transactionDTOS) {
        List<Transaction> transactions = new ArrayList<>();
        for (TransactionDTO dto : transactionDTOS) {

            Transaction transaction = new Transaction();
            Long depositorId = accountService.findUserByAccountNumber(dto.getDepositor()).getId();
            Long receiverId = accountService.findUserByAccountNumber(dto.getReceiver()).getId();

            transaction.setAmount(dto.getAmount());
            transaction.setDepositorId(depositorId);
            ;
            transaction.setReceiverId(receiverId);
            transactions.add(transaction);
        }
        return transactions;
    }*/




}
