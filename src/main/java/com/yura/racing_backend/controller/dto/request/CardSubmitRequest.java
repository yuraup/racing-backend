package com.yura.racing_backend.controller.dto.request;

public class CardSubmitRequest {
    private Long playerId;
    private int cardNumber;

    public Long getPlayerId(){
        return playerId;
    }

    public int getCardNumber(){
        return cardNumber;
    }
}
