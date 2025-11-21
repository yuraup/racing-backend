package com.yura.racing_backend.domain;

import java.util.HashMap;
import java.util.Map;

public class Round {
    private final int roundNumber;
    private final Map<Long, Integer> playerCards = new HashMap<>();
    private Integer botCardNumber;

    public Round(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public Map<Long, Integer> getPlayerCards() {
        return playerCards;
    }

    public Integer getBotCardNumber() {
        return botCardNumber;
    }

    public void submitPlayerCard(Long playerId, int cardNumber) {
        playerCards.put(playerId, cardNumber);
    }

    public void submitBotCard(int cardNumber) {
        this.botCardNumber = cardNumber;
    }
}
