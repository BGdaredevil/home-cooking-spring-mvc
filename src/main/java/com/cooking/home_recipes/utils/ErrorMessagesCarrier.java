package com.cooking.home_recipes.utils;

import java.util.ArrayList;

public class ErrorMessagesCarrier {
    public ArrayList<ErrorMessage> messages;

    public ErrorMessagesCarrier() {
        this(new ArrayList<>());
    }

    public ErrorMessagesCarrier(ArrayList<ErrorMessage> messages) {
        this.messages = messages;
    }

    public void addMessage(String msg) {
        this.addMessage(new ErrorMessage(msg));
    }

    public void addMessage(ErrorMessage msg) {
        this.messages.add(msg);
    }
}
