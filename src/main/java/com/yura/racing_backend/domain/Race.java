package com.yura.racing_backend.domain;

import com.yura.racing_backend.controller.dto.request.RaceStartRequest;
import com.yura.racing_backend.global.error.CustomException;
import com.yura.racing_backend.global.error.ErrorCode;

import java.util.*;

public class Race {
    private static final int MIN_CARD = 1;
    private static final int MAX_CARD = 45;

    private final Long id;
    private final Players players;
    private final Bots bots;
    private final int totalRounds;
    private int currentRound;
    private RaceStatus status;
    private final Map<Integer, Round> rounds = new HashMap<>();

    private final Random random = new Random();

    public Race(Long id, Players players, Bots bots, int totalRounds) {
        this.id = id;
        this.players = players;
        this.bots = bots;
        this.totalRounds = totalRounds;
        this.currentRound = 0;
        this.status = RaceStatus.READY;
    }

    public static Race of(Long raceId, RaceStartRequest request) {
        Player me = new Player("p_me", request.getPlayerName());
        Players players = new Players(List.of(me));

        int botNumbers = Math.max(1, Math.min(2, request.getBotNumbers()));
        List<String> botNames = List.of("참치마요짱", "삼김에는국물");

        List<Bot> botList = new ArrayList<>();
        for (int i = 0; i < botNumbers; i++) {
            String botId = "b_" + (i + 1);
            botList.add(new Bot(botId, botNames.get(i)));
        }
        Bots bots = new Bots(botList);

        int totalRounds = request.getTotalRounds();

        return new Race(raceId, players, bots, totalRounds);
    }

    private List<Integer> createRandomCards(int count) {
        List<Integer> cards = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int card = random.nextInt(MAX_CARD - MIN_CARD + 1) + MIN_CARD;
            cards.add(card);
        }
        return cards;
    }

    public void distributeCards() {
        if (status != RaceStatus.READY) {
            throw new CustomException(ErrorCode.INVALID_RACE_STATE);
        }

        for (Player player : players.asList()) {
            player.dealCards(createRandomCards(totalRounds));
        }

        for (Bot bot : bots.asList()) {
            bot.dealCards(createRandomCards(totalRounds));
        }

        status = RaceStatus.IN_PROGRESS;
    }

    public void submitCard(int roundNumber, String playerId, int cardNumber) {
        validateRoundNumber(roundNumber);
        Player player = players.findById(playerId);
        player.useCard(cardNumber);

        Round round = rounds.computeIfAbsent(roundNumber, Round::new);
        round.submitPlayerCard(player.getId(), cardNumber);
    }

    public Round getReadyRound(int roundNumber) {
        validateRoundNumber(roundNumber);

        Round round = rounds.get(roundNumber);
        if (round == null || round.getPlayerCards().isEmpty()) {
            throw new CustomException(ErrorCode.ROUND_NOT_READY);
        }
        return round;
    }

    public void applyRoundResult(int roundNumber, List<Player> winners) {
        increaseWinnersScore(winners);
        updateRaceStatus(roundNumber);
    }

    private void increaseWinnersScore(List<Player> winners) {
        for (Player winner : winners) {
            winner.increaseScore();
        }
    }

    private void updateRaceStatus(int roundNumber) {
        currentRound = roundNumber;
        if (status == RaceStatus.READY) {
            status = RaceStatus.IN_PROGRESS;
        }
        if (currentRound == totalRounds) {
            status = RaceStatus.FINISHED;
        }
    }

    private void validateRoundNumber(int roundNumber) {
        if (roundNumber < 1 || roundNumber > totalRounds) {
            throw new CustomException(ErrorCode.INVALID_ROUND);
        }
    }

    public Player getSinglePlayer() {
        return players.getSingle();
    }

    public List<Player> getPlayers() {
        return players.asList();
    }

    public List<Bot> getBots() {
        return bots.asList();
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public int getCurrentRound() {
        return currentRound;
    }
}
