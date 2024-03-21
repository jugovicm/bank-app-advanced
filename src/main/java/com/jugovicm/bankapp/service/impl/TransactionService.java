package com.jugovicm.bankapp.service.impl;

import com.jugovicm.bankapp.dto.TransactionDto;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {
    void saveTransaction(TransactionDto transaction);
}
