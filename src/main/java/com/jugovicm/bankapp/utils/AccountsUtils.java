package com.jugovicm.bankapp.utils;

import java.time.Year;

public class AccountsUtils {
    public static final String ACCOUNT_EXISTS_CODE="001";
    public static final String ACCOUNT_EXISTS_MESSAGE="This user already has an account created!";
    public static final String ACCOUNT_CREATION_SUCCESS="002";
    public static final String ACCOUNT_CREATION_MESSAGE="Account has been successfully created!";
    public static final String ACCOUNT_NOT_EXIST_CODE = "003";
    public static final String ACCOUNT_NOT_EXIST_MESSAGE = "User with provided account number doesn't exist!";
    public static final String ACCOUNT_FOUND_CODE = "004";
    public static final String ACCOUNT_FOUND_MESSAGE = "User account found!";

    public static final String ACCOUNT_CREDITED_SUCCESS_CODE="005";
    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE="User account was credited successfully.";
    public static final String INSUFFICIENT_BALANCE_CODE = "006";

    public static final String INSUFFICIENT_BALANCE_MESSAGE = "Insufficient balance!";
    public static final String ACCOUNT_DEBITED_SUCCESS_CODE = "007";
    public static final String ACCOUNT_DEBITED_SUCCESS_MESSAGE = "User account debited successfully.";
    public static final String TRANSFER_SUCCESSFUL_CODE="008";
    public static final String TRANSFER_SUCCESSFUL_MESSAGE="Transfer successful!";
    public static String generateAccountNumber(){
        // 2024 + rndSixDigits
        Year currentYear= Year.now ();
        int min = 100000;
        int max = 999999;

        // generate rnd num between min and max
        int rndNum = (int) Math.floor (Math.random () * (max - min + 1) + min);
        return String.valueOf ( currentYear ) + String.valueOf ( rndNum );
    }
}
