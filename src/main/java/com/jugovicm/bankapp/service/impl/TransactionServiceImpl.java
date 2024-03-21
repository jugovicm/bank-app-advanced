package com.jugovicm.bankapp.service.impl;

import com.jugovicm.bankapp.dto.TransactionDto;
import com.jugovicm.bankapp.entity.Transaction;
import com.jugovicm.bankapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        Transaction transaction = Transaction.builder ()
                .transactionType (transactionDto.getTransactionType ())
                .accountNumber ( transactionDto.getAccountNumber () )
                .amount ( transactionDto.getAmount () )
                .status ("SUCCESS" )
                .build ();
        transactionRepository.save ( transaction );
        System.out.println ("Transaction saved successfully!");
    }

}
