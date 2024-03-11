package com.jugovicm.bankapp.service.impl;

import com.jugovicm.bankapp.dto.EmailDetails;
import org.springframework.stereotype.Service;

@Service

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
}
