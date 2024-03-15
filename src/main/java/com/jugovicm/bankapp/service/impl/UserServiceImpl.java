package com.jugovicm.bankapp.service.impl;

import com.jugovicm.bankapp.dto.*;
import com.jugovicm.bankapp.entity.User;
import com.jugovicm.bankapp.repository.UserRepository;
import com.jugovicm.bankapp.utils.AccountsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

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

    // balance Enquiry, name Enquiry, credit, debit, transfer


    @Override
    public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
        // check if the provided account number exists in the db
        boolean isAccountExist = userRepository.existsByAccountNumber ( enquiryRequest.getAccountNumber ());
        if(!isAccountExist){
            return BankResponse.builder ()
                    .responseCode ( AccountsUtils.ACCOUNT_NOT_EXIST_CODE )
                    .responseMessage ( AccountsUtils.ACCOUNT_NOT_EXIST_MESSAGE )
                    .accountInfo ( null )
                    .build ();
        }
        User foundUser = userRepository.findByAccountNumber ( enquiryRequest.getAccountNumber () );
        return BankResponse.builder ()
                .responseCode ( AccountsUtils.ACCOUNT_FOUND_CODE )
                .responseMessage ( AccountsUtils.ACCOUNT_FOUND_MESSAGE )
                .accountInfo ( AccountInfo.builder ()
                            .accountBalance ( foundUser.getAccountBalance () )
                            .accountNumber ( enquiryRequest.getAccountNumber () )
                            .accountName ( foundUser.getFirstName ()+" "+ foundUser.getLastName ()+" "+ foundUser.getOtherName ())
                            .build())
                .build ();
    }

    @Override
    public String nameEnquiry(EnquiryRequest enquiryRequest) {
        boolean isAccountExist = userRepository.existsByAccountNumber ( enquiryRequest.getAccountNumber ());
        if(!isAccountExist){
            return AccountsUtils.ACCOUNT_NOT_EXIST_MESSAGE;
        }
        User foundUser = userRepository.findByAccountNumber ( enquiryRequest.getAccountNumber () );
        return foundUser.getFirstName ()+" "+ foundUser.getLastName ()+" "+ foundUser.getOtherName ();
    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest creditDebitRequest) {
        // check if account exists
        boolean isAccountExist = userRepository.existsByAccountNumber ( creditDebitRequest.getAccountNumber ());
        if(!isAccountExist){
            return BankResponse.builder ()
                    .responseCode ( AccountsUtils.ACCOUNT_NOT_EXIST_CODE )
                    .responseMessage ( AccountsUtils.ACCOUNT_NOT_EXIST_MESSAGE )
                    .accountInfo ( null )
                    .build ();
        }
        User userToCredit = userRepository.findByAccountNumber ( creditDebitRequest.getAccountNumber () );
        userToCredit.setAccountBalance ( userToCredit.getAccountBalance ().add ( creditDebitRequest.getAmount () ) );
        userRepository.save ( userToCredit );
        return BankResponse.builder ()
                .responseCode ( AccountsUtils.ACCOUNT_CREDITED_SUCCESS_CODE )
                .responseMessage (  AccountsUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .accountInfo ( AccountInfo.builder ()
                        .accountBalance ( userToCredit.getAccountBalance () )
                        .accountNumber ( userToCredit.getAccountNumber () )
                        .accountName ( userToCredit.getFirstName () + " " + userToCredit.getOtherName () + " " + userToCredit.getLastName ())
                        .build () )
                .build ();
    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest creditDebitRequest) {
        // check if account exists
        boolean isAccountExist = userRepository.existsByAccountNumber ( creditDebitRequest.getAccountNumber ());
        if(!isAccountExist){
            return BankResponse.builder ()
                    .responseCode ( AccountsUtils.ACCOUNT_NOT_EXIST_CODE )
                    .responseMessage ( AccountsUtils.ACCOUNT_NOT_EXIST_MESSAGE )
                    .accountInfo ( null )
                    .build ();
        }
        User userToDebit = userRepository.findByAccountNumber ( creditDebitRequest.getAccountNumber () );
        // check if the amount intend to withdraw is not more than the current balance
        BigInteger availableBalance = userToDebit.getAccountBalance ().toBigInteger ();
        BigInteger debitAmount = creditDebitRequest.getAmount ().toBigInteger ();
        if(availableBalance.intValue () < debitAmount.intValue ()){
            return BankResponse.builder ()
                    .responseCode ( AccountsUtils.INSUFFICIENT_BALANCE_CODE )
                    .responseMessage (  AccountsUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo (null)
                    .build ();
        }
        else{
            userToDebit.setAccountBalance ( userToDebit.getAccountBalance ().subtract ( creditDebitRequest.getAmount () ) );
            userRepository.save ( userToDebit);
            return BankResponse.builder ()
                    .responseCode ( AccountsUtils.ACCOUNT_DEBITED_SUCCESS_CODE )
                    .responseMessage (  AccountsUtils.ACCOUNT_DEBITED_SUCCESS_MESSAGE)
                    .accountInfo ( AccountInfo.builder ()
                            .accountBalance ( userToDebit.getAccountBalance () )
                            .accountNumber ( userToDebit.getAccountNumber () )
                            .accountName ( userToDebit.getFirstName () + " " + userToDebit.getOtherName () + " " + userToDebit.getLastName ())
                            .build () )
                    .build ();
        }


    }
}