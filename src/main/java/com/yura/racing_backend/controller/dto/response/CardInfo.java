package com.yura.racing_backend.controller.dto.response;

public class CardInfo {
    private String ownerName;
    private int cardNumber;

    public CardInfo(String ownerName, int cardNumber) {
        this.ownerName = ownerName;
        this.cardNumber = cardNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getCardNumber() {
        return cardNumber;
    }
}
