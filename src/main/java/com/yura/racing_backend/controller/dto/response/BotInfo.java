package com.yura.racing_backend.controller.dto.response;

public class BotInfo {
    private int cardNumber;

    public BotInfo(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCardNumber() {
        return cardNumber;
    }
}
