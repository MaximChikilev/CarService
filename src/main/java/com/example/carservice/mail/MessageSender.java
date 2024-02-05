package com.example.carservice.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class MessageSender implements EmailService {
    private final JavaMailSender javaMailSender;

    public MessageSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void SendMessages(List<SimpleMailMessage> messageList) {
        for (SimpleMailMessage message : messageList) {
            javaMailSender.send(message);
        }
    }
}
