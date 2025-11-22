package com.yura.racing_backend.domain;

import com.yura.racing_backend.controller.dto.request.RaceStartRequest;
import com.yura.racing_backend.global.error.CustomException;
import com.yura.racing_backend.global.error.ErrorCode;
import java.util.*;

public class Race {
    private static final int MIN_CARD = 1;
    private static final int MAX_CARD = 45;

    private final String id;
    private final List<Player> players;
    private final List<Bot> bots;
    private final int totalRounds;
    private int currentRound;
    private RaceStatus status;
    private final Map<Integer, Round> rounds = new HashMap<>();

    private final Random random = new Random();

    public Race(String id, List<Player> players, List<Bot> bots, int totalRounds) {
        this.id = id;
        this.players = players;
        this.bots = bots;
        this.totalRounds = totalRounds;
        this.currentRound = 0;
        this.status = RaceStatus.READY;
    }

    public static Race of(String raceId, RaceStartRequest request) {
        Player me = new Player("p_me", request.getPlayerName());
        List<Player> players = List.of(me);

        int botCount = Math.max(1, Math.min(2, request.getBotNumbers()));
        List<String> botNames = List.of("참치마요짱", "삼김에는국물");

        List<Bot> bots = new ArrayList<>();
        for (int i = 0; i < botCount; i++) {
            bots.add(new Bot("b_" + (i + 1), botNames.get(i)));
        }

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
            return;
        }

        for (Player player : players) {
            player.dealCards(createRandomCards(totalRounds));
        }

        for (Bot bot : bots) {
            bot.dealCards(createRandomCards(totalRounds));
        }

        status = RaceStatus.IN_PROGRESS;
    }

    public void submitCard(int roundNumber, String playerId, int cardNumber) {
        validateRoundNumber(roundNumber);

        Player player = findPlayerById(playerId);
        player.useCard(cardNumber);

        Round round = rounds.computeIfAbsent(roundNumber, Round::new);
        round.submitPlayerCard(player.getId(), cardNumber);
    }

    public RoundResult judgeRound(int roundNumber) {
        validateRoundNumber(roundNumber);

        Round round = rounds.get(roundNumber);
        if (round == null || round.getPlayerCards().isEmpty()) {
            throw new CustomException(ErrorCode.ROUND_NOT_READY);
        }

        Map<String, Integer> botCards = round.getBotCards();

        for (Bot bot : bots) {
            if (!botCards.containsKey(bot.getId())) {
                int card = bot.getCardForRound(roundNumber);
                round.submitBotCard(bot.getId(), card);
            }
        }

        Map<String, Integer> playerCards = round.getPlayerCards();
        botCards = round.getBotCards();

        int maxPlayer = -1;
        List<Player> winners = new ArrayList<>();

        for (Player p : players) {
            Integer card = playerCards.get(p.getId());
            if (card == null) continue;

            if (card > maxPlayer) {
                winners.clear();
                winners.add(p);
                maxPlayer = card;
            } else if (card == maxPlayer) {
                winners.add(p);
            }
        }

        Integer maxBot = null;
        for (Bot b : bots) {
            Integer card = botCards.get(b.getId());
            if (card == null) continue;

            if (maxBot == null || card > maxBot) maxBot = card;
        }

        if (maxBot != null && maxBot > maxPlayer) {
            winners.clear();
        }

        for (Player w : winners) w.increaseScore();

        currentRound = roundNumber;
        if (currentRound == totalRounds) status = RaceStatus.FINISHED;

        return new RoundResult(
                round.getRoundNumber(),
                new ArrayList<>(players),
                new ArrayList<>(bots),
                new HashMap<>(playerCards),
                new HashMap<>(botCards)
        );
    }

    public void finish() {
        this.status = RaceStatus.FINISHED;
    }

    private void validateRoundNumber(int roundNumber) {
        if (roundNumber < 1 || roundNumber > totalRounds) {
            throw new CustomException(ErrorCode.INVALID_ROUND);
        }
    }

    private Player findPlayerById(String playerId) {
        return players.stream()
                .filter(p -> p.getId().equals(playerId))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PLAYER));
    }

    public String getId() {
        return id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Bot> getBots() {
        return bots;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public RaceStatus getStatus() {
        return status;
    }
}
