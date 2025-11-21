package com.yura.racing_backend.controller.dto.response;

import com.yura.racing_backend.domain.Player;
import com.yura.racing_backend.domain.Race;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public static RaceStatusResponse from(Race race) {
        List<PlayerInfo> ranking = race.getPlayers().stream()
                .sorted(Comparator.comparingInt(Player::getScore).reversed())
                .map(player -> new PlayerInfo(player.getName(), player.getScore()))
                .collect(Collectors.toList());

        return new RaceStatusResponse(
                race.getCurrentRound(),
                race.getTotalRounds(),
                ranking
        );
    }
}

