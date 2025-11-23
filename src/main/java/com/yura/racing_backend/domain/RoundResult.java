package com.yura.racing_backend.domain;

import java.util.List;
import java.util.Map;

public class RoundResult {
    private final int roundNumber;
    private final List<Player> players;
    private final List<Bot> bots;
    private final Map<String, Integer> playerCards;
    private final Map<String, Integer> botCards;

    public RoundResult(
            int roundNumber,
            List<Player> players,
            List<Bot> bots,
            Map<String, Integer> playerCards,
            Map<String, Integer> botCards
    ) {
        this.roundNumber = roundNumber;
        this.players = players;
        this.bots = bots;
        this.playerCards = playerCards;
        this.botCards = botCards;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Bot> getBots() {
        return bots;
    }

    public Map<String, Integer> getPlayerCards() {
        return playerCards;
    }

    public Map<String, Integer> getBotCards() {
        return botCards;
    }
}