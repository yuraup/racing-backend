package com.yura.racing_backend.domain;

import java.util.List;
import java.util.Map;

public class RoundResult {
    private final int roundNumber;
    private final List<Player> players;
    private final Bot bot;
    private final Map<Long, Integer> playerCards;
    private final Integer botCardNumber;

    public RoundResult(int roundNumber,
                       List<Player> players,
                       Bot bot,
                       Map<Long, Integer> playerCards,
                       Integer botCardNumber) {
        this.roundNumber = roundNumber;
        this.players = players;
        this.bot = bot;
        this.playerCards = playerCards;
        this.botCardNumber = botCardNumber;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Bot getBot() {
        return bot;
    }

    public Map<Long, Integer> getPlayerCards() {
        return playerCards;
    }

    public Integer getBotCardNumber() {
        return botCardNumber;
    }
}
