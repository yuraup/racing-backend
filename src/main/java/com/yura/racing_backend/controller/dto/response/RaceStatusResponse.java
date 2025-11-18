package com.yura.racing_backend.controller.dto.response;

import java.util.List;

public class RaceStatusResponse {
    private int currentRound;
    private int totalRounds;
    private List<PlayerInfo> ranking;

    public RaceStatusResponse(int currentRound, int totalRounds, List<PlayerInfo> ranking) {
        this.currentRound = currentRound;
        this.totalRounds = totalRounds;
        this.ranking = ranking;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public List<PlayerInfo> getRanking() {
        return ranking;
    }
}
