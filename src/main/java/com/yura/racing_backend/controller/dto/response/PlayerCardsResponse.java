package com.yura.racing_backend.controller.dto.response;

import java.util.List;

public class PlayerCardsResponse {

    private final String playerId;
    private final String playerName;
    private final List<Integer> cards;

    public PlayerCardsResponse(String playerId, String playerName, List<Integer> cards) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.cards = cards;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<Integer> getCards() {
        return cards;
    }
}
