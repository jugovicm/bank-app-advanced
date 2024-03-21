package com.jugovicm.bankapp.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class TransactionDto {
    private String transactionType;
    private BigDecimal amount;
    private String accountNumber;
    private String status;
}




