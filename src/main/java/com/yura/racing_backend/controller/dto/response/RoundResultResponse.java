package com.yura.racing_backend.controller.dto.response;

import java.util.List;

public class RoundResultResponse {
    private int roundNumber;
    private List<PlayerInfo> players;
    private BotInfo bot;
    private List<CardInfo> cards;

    public RoundResultResponse(int roundNumber, List<PlayerInfo> players, BotInfo bot, List<CardInfo> cards) {
        this.roundNumber = roundNumber;
        this.players = players;
        this.bot = bot;
        this.cards = cards;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public List<PlayerInfo> getPlayers() {
        return players;
    }

    public BotInfo getBot() {
        return bot;
    }

    public List<CardInfo> getCards() {
        return cards;
    }
}
