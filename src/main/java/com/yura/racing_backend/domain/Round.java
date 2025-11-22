package com.yura.racing_backend.domain;

import java.util.HashMap;
import java.util.Map;

public class Round {
    private final int roundNumber;
    private final Map<String, Integer> playerCards = new HashMap<>();
    private final Map<String, Integer> botCards = new HashMap<>();

    public Round(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public Map<String, Integer> getPlayerCards() {
        return playerCards;
    }

    public Map<String, Integer> getBotCards() {
        return botCards;
    }

    public void submitPlayerCard(String playerId, int cardNumber) {
        playerCards.put(playerId, cardNumber);
    }

    public void submitBotCard(String botId, int cardNumber) {
        botCards.put(botId, cardNumber);
    }
}
