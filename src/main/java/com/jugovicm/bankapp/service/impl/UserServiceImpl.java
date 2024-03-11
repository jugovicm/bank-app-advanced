package com.jugovicm.bankapp.service.impl;

import com.jugovicm.bankapp.dto.AccountInfo;
import com.jugovicm.bankapp.dto.BankResponse;
import com.jugovicm.bankapp.dto.EmailDetails;
import com.jugovicm.bankapp.dto.UserRequest;
import com.jugovicm.bankapp.entity.User;
import com.jugovicm.bankapp.repository.UserRepository;
import com.jugovicm.bankapp.utils.AccountsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Override
    public BankResponse createAccount(UserRequest userRequest) {
        // Create new user - save details of new user into db
        // Check ih user already has an account
        if (userRepository.existsByEmail ( userRequest.getEmail () )) {
            return BankResponse.builder ()
                    .responseCode ( AccountsUtils.ACCOUNT_EXISTS_CODE )
                    .responseMessage ( AccountsUtils.ACCOUNT_EXISTS_MESSAGE )
                    .accountInfo ( null )
                    .build ();
        }
        User newUser = User.builder ()
                .firstName ( userRequest.getFirstName () )
                .lastName ( userRequest.getLastName () )
                .otherName ( userRequest.getOtherName () )
                .gender ( userRequest.getGender () )
                .address ( userRequest.getAddress () )
                .stateOfOrigin ( userRequest.getStateOfOrigin () )
                .accountNumber ( AccountsUtils.generateAccountNumber () )
                .accountBalance ( BigDecimal.ZERO )
                .email ( userRequest.getEmail () )
                .phoneNumber ( userRequest.getPhoneNumber () )
                .alternativePhoneNumber ( userRequest.getAlternativePhoneNumber () )
                .status ( "ACTIVE" )
                .build ();

        User savedUser = userRepository.save ( newUser );

        EmailDetails emailDetails = EmailDetails.builder ()
                .recipient ( savedUser.getEmail () )
                .messageBody ("Congratulations! Your account has been successfully created! \nYour account details:\n" +
                "Account Name: " + savedUser.getFirstName () + " " + savedUser.getLastName () + " " + savedUser.getOtherName () + "\n" +
                        "Account number: " + savedUser.getAccountNumber ())
                .subject ( "ACCOUNT CREATION" )
                .build ();

        emailService.sendEmailAlert (emailDetails);

        return BankResponse.builder ()
                .responseCode ( AccountsUtils.ACCOUNT_CREATION_SUCCESS )
                .responseMessage ( AccountsUtils.ACCOUNT_CREATION_MESSAGE )
                .accountInfo ( AccountInfo.builder ()
                        .accountBalance ( savedUser.getAccountBalance () )
                        .accountNumber ( savedUser.getAccountNumber () )
                        .accountName ( savedUser.getFirstName () + " " + savedUser.getLastName () + " " + savedUser.getOtherName () )
                        .build ())
                .build ();
    }
}