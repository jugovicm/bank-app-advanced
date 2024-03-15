package com.jugovicm.bankapp.service.impl;

import com.jugovicm.bankapp.dto.BankResponse;
import com.jugovicm.bankapp.dto.CreditDebitRequest;
import com.jugovicm.bankapp.dto.EnquiryRequest;
import com.jugovicm.bankapp.dto.UserRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    BankResponse createAccount(UserRequest userRequest);
    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
    String nameEnquiry(EnquiryRequest enquiryRequest);
    BankResponse creditAccount(CreditDebitRequest creditDebitRequest);
    BankResponse debitAccount(CreditDebitRequest creditDebitRequest);
}
