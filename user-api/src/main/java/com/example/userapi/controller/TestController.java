package com.example.userapi.controller;

import com.example.userapi.service.EmailSendService;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final EmailSendService emailSendService;

    @GetMapping
    public void sendTestEmail(){
        String re = emailSendService.sendEmail();
        System.out.println(re);
    }
}
