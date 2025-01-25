package com.example.JournalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {
    @Autowired
    private EmailService emailService;

    @Test
    public void test()
    {
        emailService.sendMail("tejas1372003@gmail.com", "Hello Mesaage", "Hello..have a nice day..!!");
    }
}
