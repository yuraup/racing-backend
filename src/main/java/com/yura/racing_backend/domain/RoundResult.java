package com.yura.racing_backend.domain;

import java.util.List;
import java.util.Map;

public class RoundResult {

    private final int roundNumber;
    private final List<Player> players;
    private final List<Bot> bots;
    private final Map<Long, Integer> playerCards;
    private final Map<Long, Integer> botCards;

    public RoundResult(
            int roundNumber,
            List<Player> players,
            List<Bot> bots,
            Map<Long, Integer> playerCards,
            Map<Long, Integer> botCards
    ) {
        this.roundNumber = roundNumber;
        this.players = players;
        this.bots = bots;
        this.playerCards = playerCards;
        this.botCards = botCards;
    }

    public int getRoundNumber() { return roundNumber; }
    public List<Player> getPlayers() { return players; }
    public List<Bot> getBots() { return bots; }
    public Map<Long, Integer> getPlayerCards() { return playerCards; }
    public Map<Long, Integer> getBotCards() { return botCards; }
}
