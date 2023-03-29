package com.example.userapi.service;

import com.example.userapi.domain.SignupForm;
import com.example.userapi.domain.model.Customer;
import com.example.userapi.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpCustomerService {

    private final CustomerRepository customerRepository;
    public Customer signUp(SignupForm form){
        return customerRepository.save(Customer.from(form));
    }
}
