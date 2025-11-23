package com.yura.racing_backend.controller.dto.response;

import java.util.List;

public class RaceStatusResponse {
    private final int currentRound;
    private final int totalRounds;
    private final List<PlayerInfo> ranking;

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
