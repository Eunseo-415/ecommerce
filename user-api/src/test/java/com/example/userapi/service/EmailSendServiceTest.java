package com.example.userapi.service;

import com.example.userapi.config.FeignConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailSendServiceTest {

    @Autowired
    private EmailSendService emailSendService;

    @Test
    void sendEmail() {
        String res = emailSendService.sendEmail();
        System.out.println(res);
    }
}