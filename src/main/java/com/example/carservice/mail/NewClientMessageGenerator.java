package com.example.carservice.mail;

import com.example.carservice.entity.Client;

public class NewClientMessageGenerator extends AbstractMessageGenerator<Client> {

    @Override
    protected String getText(Client element) {
        return String.format(
                "Dear %s, You have been registered in the system. Your login is %s, password is your phone number",
                element.getFirstName(),
                element.getFirstName()+element.getSecondName());
    }

    @Override
    protected String getSubject() {
        return "Car Service. You have been registered in the system ";
    }

    @Override
    protected String getTo(Client element) {
        return element.getEmail();
    }
}
