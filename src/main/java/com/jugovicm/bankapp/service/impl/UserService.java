package com.jugovicm.bankapp.service.impl;

import com.jugovicm.bankapp.dto.BankResponse;
import com.jugovicm.bankapp.dto.UserRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    BankResponse createAccount(UserRequest userRequest);
}
