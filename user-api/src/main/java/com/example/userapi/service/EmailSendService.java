package com.example.userapi.service;

import com.example.userapi.client.MailgunClient;
import com.example.userapi.client.mailgun.SendMailForm;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendService {
    private final MailgunClient mailgunClient;

    public String sendEmail(){

        SendMailForm form = SendMailForm.builder()
                .from("eumseo@icloud.com")
                .to("ieunseo415@gmail.com")
                .subject("test mail ")
                .text("my text")
                .build();

        return mailgunClient.sendEmail(form).toString();
    }
}
