package com.yura.racing_backend.controller.dto.request;

import java.util.List;

public class RaceStartRequest {
    private List<String> playerNames;
    private boolean botEnabled;
    private int totalRounds;

    public List<String> getPlayerNames() {
        return playerNames;
    }

    public boolean isBotEnabled() {
        return botEnabled;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

}
