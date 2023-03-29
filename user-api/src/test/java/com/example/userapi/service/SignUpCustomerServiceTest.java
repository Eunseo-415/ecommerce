package com.example.userapi.service;

import com.example.userapi.domain.SignupForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SignUpCustomerServiceTest {

    @Autowired
    private SignUpCustomerService service;

    @Test
    void signUp() {
        SignupForm form = SignupForm.builder()
                .name("name")
                .birth(LocalDate.now())
                .email("test@email.com")
                .password("1")
                .phone("01000000000")
                .build();
        Assert.notNull(service.signUp(form).getId());
    }
}