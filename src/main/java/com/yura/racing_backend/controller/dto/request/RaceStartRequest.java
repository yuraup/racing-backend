package com.yura.racing_backend.controller.dto.request;

public class RaceStartRequest {
    private int totalRounds;
    private int botNumbers;
    private String playerName;

    public int getTotalRounds() {
        return totalRounds;
    }

    public int getBotNumbers() {
        return botNumbers;
    }

    public String getPlayerName() {
        return playerName;
    }
}
