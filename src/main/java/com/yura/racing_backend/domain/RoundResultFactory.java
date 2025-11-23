package com.yura.racing_backend.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoundResultFactory {

    private RoundResultFactory() {
    }

    public static RoundResult create(Round round, List<Player> players, List<Bot> bots) {
        List<Player> copiedPlayers = new ArrayList<>(players);
        List<Bot> copiedBots = new ArrayList<>(bots);

        Map<String, Integer> playerCards = new HashMap<>(round.getPlayerCards());
        Map<String, Integer> botCards = new HashMap<>(round.getBotCards());

        return new RoundResult(
                round.getRoundNumber(),
                copiedPlayers,
                copiedBots,
                playerCards,
                botCards
        );
    }
}
