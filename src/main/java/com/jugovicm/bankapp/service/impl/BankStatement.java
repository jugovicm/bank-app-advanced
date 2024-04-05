package com.jugovicm.bankapp.service.impl;

import com.jugovicm.bankapp.entity.Transaction;
import com.jugovicm.bankapp.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BankStatement {

    private TransactionRepository transactionRepository;
    /**
    * retrieve list of transactions within a date range given an account number
     * generate pdf file of transaction
     * send the file via email
    */

    public List<Transaction> generateStatements(String accountNumber, String startDate, String endDate){
        LocalDate start = LocalDate.parse ( startDate, DateTimeFormatter.ISO_DATE );
        LocalDate end = LocalDate.parse ( endDate,DateTimeFormatter.ISO_DATE );
        List<Transaction> transactionList = transactionRepository.findAll ().stream ()
                .filter ( transaction -> transaction.getAccountNumber ().equals ( accountNumber ) )
                .filter ( transaction -> transaction.getCreatedAt ().isEqual ( start ))
                .filter ( transaction -> transaction.getCreatedAt ().isEqual ( end ) )
                .collect( Collectors.toList());

        return transactionList;
    }
}
