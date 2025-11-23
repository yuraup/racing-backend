package com.yura.racing_backend.controller.dto.response;

import java.util.List;

public class RoundResultResponse {

    private final int roundNumber;
    private final List<PlayerInfo> players;
    private final List<BotInfo> bots;
    private final List<CardInfo> cards;

    public RoundResultResponse(
            int roundNumber,
            List<PlayerInfo> players,
            List<BotInfo> bots,
            List<CardInfo> cards
    ) {
        this.roundNumber = roundNumber;
        this.players = players;
        this.bots = bots;
        this.cards = cards;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public List<PlayerInfo> getPlayers() {
        return players;
    }

    public List<BotInfo> getBots() {
        return bots;
    }

    public List<CardInfo> getCards() {
        return cards;
    }
}