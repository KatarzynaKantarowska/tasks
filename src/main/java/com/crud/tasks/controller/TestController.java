package com.crud.tasks.controller;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.service.SimpleEmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Optional.ofNullable;

@RestController
@RequestMapping("v1")
@AllArgsConstructor
public class TestController {

    private final SimpleEmailService emailService;
    private final AdminConfig adminConfig;

    @GetMapping("test")
    void abc(){
       String  newCard = "string";
        Mail mail = new Mail(
                "SUBJECT",
                "New card: ",
                adminConfig.getAdminMail(),
                null
        );
        ofNullable(newCard).ifPresent(card -> emailService.send(mail));
    }
}
