package com.yura.racing_backend.controller.dto.response;

import com.yura.racing_backend.domain.Bot;
import com.yura.racing_backend.domain.Player;
import com.yura.racing_backend.domain.RoundResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public static RoundResultResponse from(RoundResult result) {
        List<PlayerInfo> playerInfos = result.getPlayers().stream()
                .map(player -> new PlayerInfo(player.getName(), player.getScore()))
                .collect(Collectors.toList());

        List<BotInfo> botInfos = new ArrayList<>();
        Map<String, Integer> botCards = result.getBotCards();

        for (Bot bot : result.getBots()) {
            Integer cardNumber = botCards.get(bot.getId());
            if (cardNumber != null) {
                botInfos.add(new BotInfo(bot.getName(), cardNumber));
            }
        }

        List<CardInfo> cardInfos = new ArrayList<>();
        Map<String, Integer> playerCards = result.getPlayerCards();

        for (Player player : result.getPlayers()) {
            Integer cardNumber = playerCards.get(player.getId());
            if (cardNumber != null) {
                cardInfos.add(new CardInfo(player.getName(), cardNumber));
            }
        }

        for (Bot bot : result.getBots()) {
            Integer cardNumber = botCards.get(bot.getId());
            if (cardNumber != null) {
                cardInfos.add(new CardInfo(bot.getName(), cardNumber));
            }
        }

        return new RoundResultResponse(
                result.getRoundNumber(),
                playerInfos,
                botInfos,
                cardInfos
        );
    }
}
