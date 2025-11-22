package com.yura.racing_backend.controller.dto.response;

import com.yura.racing_backend.domain.Bot;
import com.yura.racing_backend.domain.Player;
import com.yura.racing_backend.domain.RoundResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RoundResultResponse {

    private int roundNumber;
    private List<PlayerInfo> players;
    private List<BotInfo> bots;
    private List<CardInfo> cards;

    public RoundResultResponse(int roundNumber, List<PlayerInfo> players, List<BotInfo> bots, List<CardInfo> cards) {
        this.roundNumber = roundNumber;
        this.players = players;
        this.bots = bots;
        this.cards = cards;
    }

    public static RoundResultResponse from(RoundResult result) {

        List<PlayerInfo> playerInfos = result.getPlayers().stream()
                .map(p -> new PlayerInfo(p.getName(), p.getScore()))
                .collect(Collectors.toList());

        List<BotInfo> botInfos = new ArrayList<>();
        Map<Long, Integer> botCards = result.getBotCards();

        for (Bot bot : result.getBots()) {
            Integer cardNumber = botCards.get(bot.getId());
            botInfos.add(new BotInfo(bot.getName(), cardNumber != null ? cardNumber : -1));
        }

        List<CardInfo> cardInfos = new ArrayList<>();
        Map<Long, Integer> playerCards = result.getPlayerCards();

        for (Player player : result.getPlayers()) {
            Integer card = playerCards.get(player.getId());
            if (card != null) {
                cardInfos.add(new CardInfo(player.getName(), card));
            }
        }

        for (Bot bot : result.getBots()) {
            Integer card = botCards.get(bot.getId());
            if (card != null) {
                cardInfos.add(new CardInfo(bot.getName(), card));
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
