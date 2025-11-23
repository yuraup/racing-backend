package com.yura.racing_backend.service;

import com.yura.racing_backend.controller.dto.request.CardSubmitRequest;
import com.yura.racing_backend.controller.dto.request.RaceStartRequest;
import com.yura.racing_backend.controller.dto.response.BotInfo;
import com.yura.racing_backend.controller.dto.response.CardInfo;
import com.yura.racing_backend.controller.dto.response.PlayerCardsResponse;
import com.yura.racing_backend.controller.dto.response.PlayerInfo;
import com.yura.racing_backend.controller.dto.response.RaceStatusResponse;
import com.yura.racing_backend.controller.dto.response.RoundResultResponse;
import com.yura.racing_backend.domain.Bot;
import com.yura.racing_backend.domain.Player;
import com.yura.racing_backend.domain.Race;
import com.yura.racing_backend.domain.Round;
import com.yura.racing_backend.domain.RoundResult;
import com.yura.racing_backend.domain.RoundResultFactory;
import com.yura.racing_backend.global.error.CustomException;
import com.yura.racing_backend.global.error.ErrorCode;
import com.yura.racing_backend.util.RaceIdGenerator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class RaceServiceImpl implements RaceService {

    private final Map<Long, Race> raceStore = new ConcurrentHashMap<>();

    @Override
    public Long createRace(RaceStartRequest request) {
        Long raceId = RaceIdGenerator.generate();
        Race race = Race.of(raceId, request);
        raceStore.put(raceId, race);
        return raceId;
    }

    @Override
    public void distributeCards(Long raceId) {
        Race race = findRaceOrThrow(raceId);
        race.distributeCards();
    }

    @Override
    public void submitCard(Long raceId, int round, CardSubmitRequest request) {
        Race race = findRaceOrThrow(raceId);
        race.submitCard(round, request.getPlayerId(), request.getCardNumber());
    }

    @Override
    public RoundResultResponse judgeRound(Long raceId, int roundNumber) {
        Race race = findRaceOrThrow(raceId);
        Round round = race.getReadyRound(roundNumber);
        submitBotCardsIfNecessary(race, roundNumber, round);

        Map<String, Integer> playerCards = round.getPlayerCards();
        Map<String, Integer> botCards = round.getBotCards();

        List<Player> winners = findWinners(race.getPlayers(), race.getBots(), playerCards, botCards);
        race.applyRoundResult(roundNumber, winners);
        RoundResult result = RoundResultFactory.create(round, race.getPlayers(), race.getBots());

        List<PlayerInfo> playerInfos = result.getPlayers().stream()
                .map(player -> new PlayerInfo(player.getName(), player.getScore()))
                .collect(Collectors.toList());

        Map<String, Integer> resultBotCards = result.getBotCards();
        List<BotInfo> botInfos = result.getBots().stream()
                .map(bot -> {
                    Integer cardNumber = resultBotCards.get(bot.getId());
                    if (cardNumber == null) {
                        return null;
                    }
                    return new BotInfo(bot.getName(), cardNumber);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<CardInfo> cardInfos = new ArrayList<>();

        Map<String, Integer> resultPlayerCards = result.getPlayerCards();

        for (Player player : result.getPlayers()) {
            Integer cardNumber = resultPlayerCards.get(player.getId());
            if (cardNumber != null) {
                cardInfos.add(new CardInfo(player.getName(), cardNumber));
            }
        }

        for (Bot bot : result.getBots()) {
            Integer cardNumber = resultBotCards.get(bot.getId());
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

    @Override
    public RaceStatusResponse getRaceStatus(Long raceId) {
        Race race = findRaceOrThrow(raceId);
        List<PlayerInfo> ranking = createRanking(race);

        return new RaceStatusResponse(
                race.getCurrentRound(),
                race.getTotalRounds(),
                ranking
        );
    }

    @Override
    public PlayerCardsResponse getPlayerCards(Long raceId) {
        Race race = findRaceOrThrow(raceId);
        Player player = race.getSinglePlayer();
        return PlayerCardsResponse.from(player);
    }

    private Race findRaceOrThrow(Long raceId) {
        Race race = raceStore.get(raceId);
        if (race == null) {
            throw new CustomException(ErrorCode.INVALID_RACE);
        }
        return race;
    }

    private List<PlayerInfo> createRanking(Race race) {
        return race.getPlayers().stream()
                .sorted(Comparator.comparingInt(Player::getScore).reversed())
                .map(player -> new PlayerInfo(player.getName(), player.getScore()))
                .collect(Collectors.toList());
    }

    private void submitBotCardsIfNecessary(Race race, int roundNumber, Round round) {
        Map<String, Integer> botCards = round.getBotCards();

        for (Bot bot : race.getBots()) {
            if (!botCards.containsKey(bot.getId())) {
                int botCard = bot.getCardForRound(roundNumber);
                round.submitBotCard(bot.getId(), botCard);
            }
        }
    }

    private List<Player> findWinners(
            List<Player> players,
            List<Bot> bots,
            Map<String, Integer> playerCards,
            Map<String, Integer> botCards
    ) {
        int maxPlayerCard = -1;
        List<Player> winners = new ArrayList<>();

        for (Player player : players) {
            Integer cardNumber = playerCards.get(player.getId());
            if (cardNumber == null) {
                continue;
            }

            if (cardNumber > maxPlayerCard) {
                maxPlayerCard = cardNumber;
                winners.clear();
                winners.add(player);
            } else if (cardNumber == maxPlayerCard) {
                winners.add(player);
            }
        }

        Integer maxBotCard = null;
        for (Bot bot : bots) {
            Integer cardNumber = botCards.get(bot.getId());
            if (cardNumber == null) {
                continue;
            }
            if (maxBotCard == null || cardNumber > maxBotCard) {
                maxBotCard = cardNumber;
            }
        }

        if (maxBotCard != null && maxBotCard > maxPlayerCard) {
            winners.clear();
        }

        return winners;
    }
}
