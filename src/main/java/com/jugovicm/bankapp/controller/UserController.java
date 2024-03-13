package com.jugovicm.bankapp.controller;

import com.jugovicm.bankapp.dto.BankResponse;
import com.jugovicm.bankapp.dto.EnquiryRequest;
import com.jugovicm.bankapp.dto.UserRequest;
import com.jugovicm.bankapp.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public BankResponse createAccount(@RequestBody UserRequest userRequest){
        return userService.createAccount ( userRequest );
    }

    @GetMapping("balanceEnquiry")
    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return userService.balanceEnquiry ( enquiryRequest );
    }

    @GetMapping("nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return userService.nameEnquiry ( enquiryRequest );
    }
}
