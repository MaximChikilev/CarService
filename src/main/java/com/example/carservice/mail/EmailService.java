package com.example.carservice.mail;

import org.springframework.mail.SimpleMailMessage;

import java.util.List;

public interface EmailService {
  void SendMessages(List<SimpleMailMessage> messageList);
}
