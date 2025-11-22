package com.yura.racing_backend.domain;

import java.util.HashMap;
import java.util.Map;

public class Round {

    private final int roundNumber;
    private final Map<Long, Integer> playerCards = new HashMap<>();
    private final Map<Long, Integer> botCards = new HashMap<>();

    public Round(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public int getRoundNumber() { return roundNumber; }

    public Map<Long, Integer> getPlayerCards() { return playerCards; }

    public Map<Long, Integer> getBotCards() { return botCards; }

    public void submitPlayerCard(Long playerId, int cardNumber) {
        playerCards.put(playerId, cardNumber);
    }

    public void submitBotCard(Long botId, int cardNumber) {
        botCards.put(botId, cardNumber);
    }
}
