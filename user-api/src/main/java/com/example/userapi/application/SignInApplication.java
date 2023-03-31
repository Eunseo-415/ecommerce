package com.example.userapi.application;

import com.example.domain.config.JwtAuthenticationProvider;
import com.example.domain.domain.common.UserType;
import com.example.userapi.domain.SignInForm;
import com.example.userapi.domain.model.Customer;
import com.example.userapi.exception.CustomException;
import com.example.userapi.exception.ErrorCode;
import com.example.userapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInApplication {

    private final CustomerService customerService;
    private final JwtAuthenticationProvider provider;

    public String customerLoginToken(SignInForm form){
        Customer c = customerService.findValidCustomer(form.getEmail(), form.getPassword())
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_CHECK_FAIL));


        return provider.createToken(c.getEmail(), c.getId(), UserType.CUSTOMER);

    }
}
