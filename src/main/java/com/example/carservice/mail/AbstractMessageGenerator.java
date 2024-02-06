package com.example.carservice.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMessageGenerator<T> {
  @Value("${spring.mail.username}")
  private String fromAddress;

  protected String text;

  protected abstract String getText(T element);

  protected abstract String getSubject();

  protected abstract String getTo(T element);

  public SimpleMailMessage getMessage(T element) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setSubject(getSubject());
    message.setText(getText(element));
    message.setTo(getTo(element));
    message.setFrom(fromAddress);
    return message;
  }

  public List<SimpleMailMessage> getSimplMailMessageList(List<T> list) {
    var messageList = new ArrayList<SimpleMailMessage>();
    for (T element : list) {
      messageList.add(getMessage(element));
    }
    return messageList;
  }
}
