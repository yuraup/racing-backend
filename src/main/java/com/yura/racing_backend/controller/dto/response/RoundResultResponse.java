package com.yura.racing_backend.controller.dto.response;

import com.yura.racing_backend.domain.Player;
import com.yura.racing_backend.domain.RoundResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public static RoundResultResponse from(RoundResult result) {
        List<PlayerInfo> playerInfos = result.getPlayers().stream()
                .map(player -> new PlayerInfo(player.getName(), player.getScore()))
                .collect(Collectors.toList());

        BotInfo botInfo = null;
        if (result.getBot() != null && result.getBotCardNumber() != null) {
            botInfo = new BotInfo(result.getBotCardNumber());
        }

        List<CardInfo> cardInfos = new ArrayList<>();
        Map<Long, Integer> playerCards = result.getPlayerCards();

        for (Player player : result.getPlayers()) {
            Integer cardNumber = playerCards.get(player.getId());
            if (cardNumber != null) {
                cardInfos.add(new CardInfo(player.getName(), cardNumber));
            }
        }

        if (result.getBot() != null && result.getBotCardNumber() != null) {
            cardInfos.add(new CardInfo(result.getBot().getName(), result.getBotCardNumber()));
        }

        return new RoundResultResponse(
                result.getRoundNumber(),
                playerInfos,
                botInfo,
                cardInfos
        );
    }
}
