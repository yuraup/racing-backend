package com.yura.racing_backend.domain;

import com.yura.racing_backend.controller.dto.request.RaceStartRequest;
import java.util.*;

public class Race {
    private final Long id;
    private final List<Player> players;
    private final Bot bot;
    private final int totalRounds;
    private int currentRound;
    private RaceStatus status;
    private final Map<Integer, Round> rounds = new HashMap<>();

    public Race(Long id, List<Player> players, Bot bot, int totalRounds) {
        this.id = id;
        this.players = players;
        this.bot = bot;
        this.totalRounds = totalRounds;
        this.currentRound = 0;
        this.status = RaceStatus.READY;
    }

    public static Race of(Long raceId, RaceStartRequest request) {
        List<String> playerNames = request.getPlayerNames();
        List<Player> players = new ArrayList<>();

        long playerIdSequence = 1L;
        for (String name : playerNames) {
            players.add(new Player(playerIdSequence++, name));
        }

        Bot bot = request.isBotEnabled() ? new Bot() : null;
        int totalRounds = request.getTotalRounds();

        return new Race(raceId, players, bot, totalRounds);
    }

    public void distributeCards() {
        if (status == RaceStatus.READY) {
            status = RaceStatus.IN_PROGRESS;
        }
    }

    public void submitCard(int roundNumber, Long playerId, int cardNumber) {
        validateRoundNumber(roundNumber);

        Player player = findPlayerById(playerId);

        Round round = rounds.computeIfAbsent(roundNumber, Round::new);
        round.submitPlayerCard(player.getId(), cardNumber);
    }

    public RoundResult judgeRound(int roundNumber) {
        validateRoundNumber(roundNumber);

        Round round = rounds.get(roundNumber);
        if (round == null || round.getPlayerCards().isEmpty()) {
            throw new IllegalStateException("아직 카드를 제출하지 않은 라운드입니다.");
        }

        Random random = new Random();

        if (bot != null && round.getBotCardNumber() == null) {
            int botCard = random.nextInt(45) + 1;
            round.submitBotCard(botCard);
        }

        Map<Long, Integer> playerCards = round.getPlayerCards();
        Integer botCardNumber = round.getBotCardNumber();

        int max = -1;
        List<Player> winners = new ArrayList<>();

        for (Player player : players) {
            Integer cardNumber = playerCards.get(player.getId());
            if (cardNumber == null) {
                continue;
            }

            if (cardNumber > max) {
                max = cardNumber;
                winners.clear();
                winners.add(player);
            } else if (cardNumber == max) {
                winners.add(player);
            }
        }

        if (botCardNumber != null && botCardNumber > max) {
            winners.clear();
        }

        for (Player winner : winners) {
            winner.increaseScore();
        }

        currentRound = roundNumber;
        if (status == RaceStatus.READY) {
            status = RaceStatus.IN_PROGRESS;
        }
        if (currentRound == totalRounds) {
            status = RaceStatus.FINISHED;
        }

        return new RoundResult(
                round.getRoundNumber(),
                new ArrayList<>(players),
                bot,
                new HashMap<>(playerCards),
                botCardNumber
        );
    }

    public void finish() {
        this.status = RaceStatus.FINISHED;
    }

    private void validateRoundNumber(int roundNumber) {
        if (roundNumber < 1 || roundNumber > totalRounds) {
            throw new IllegalArgumentException("유효하지 않은 라운드 번호입니다. round=" + roundNumber);
        }
    }

    private Player findPlayerById(Long playerId) {
        return players.stream()
                .filter(player -> player.getId().equals(playerId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 플레이어가 없습니다. id=" + playerId));
    }

    public Long getId() {
        return id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Bot getBot() {
        return bot;
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

    public Map<Integer, Round> getRounds() {
        return Collections.unmodifiableMap(rounds);
    }
}
