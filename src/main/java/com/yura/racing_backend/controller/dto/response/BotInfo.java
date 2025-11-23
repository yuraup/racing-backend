package com.yura.racing_backend.controller.dto.response;

public class BotInfo {
    private final String name;
    private final int cardNumber;

    public BotInfo(String name, int cardNumber) {
        this.name = name;
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return name;
    }

    public int getCardNumber() {
        return cardNumber;
    }
}