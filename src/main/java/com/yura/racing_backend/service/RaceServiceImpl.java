package com.yura.racing_backend.service;

import com.yura.racing_backend.controller.dto.request.CardSubmitRequest;
import com.yura.racing_backend.controller.dto.request.RaceStartRequest;
import com.yura.racing_backend.controller.dto.response.RaceStatusResponse;
import com.yura.racing_backend.controller.dto.response.RoundResultResponse;
import com.yura.racing_backend.domain.Race;
import com.yura.racing_backend.domain.RoundResult;
import com.yura.racing_backend.global.error.CustomException;
import com.yura.racing_backend.global.error.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RaceServiceImpl implements RaceService {
    private final Map<String, Race> raceStore = new ConcurrentHashMap<>();

    @Override
    public String createRace(RaceStartRequest request) {
        String raceId = "r_" + UUID.randomUUID().toString().substring(0, 8);
        Race race = Race.of(raceId, request);
        raceStore.put(raceId, race);
        return raceId;
    }

    private Race findRaceOrThrow(String raceId) {
        Race race = raceStore.get(raceId);
        if (race == null) {
            throw new CustomException(ErrorCode.INVALID_RACE);
        }
        return race;
    }

    @Override
    public void distributeCards(String raceId) {
        Race race = findRaceOrThrow(raceId);
        race.distributeCards();
    }

    @Override
    public void submitCard(String raceId, int round, CardSubmitRequest request) {
        Race race = findRaceOrThrow(raceId);
        race.submitCard(round, request.getPlayerId(), request.getCardNumber());
    }

    @Override
    public RoundResultResponse judgeRound(String raceId, int round) {
        Race race = findRaceOrThrow(raceId);
        RoundResult result = race.judgeRound(round);
        return RoundResultResponse.from(result);
    }

    @Override
    public RaceStatusResponse getRaceStatus(String raceId) {
        Race race = findRaceOrThrow(raceId);
        return RaceStatusResponse.from(race);
    }

    @Override
    public void finishRace(String raceId) {
        Race race = findRaceOrThrow(raceId);
        race.finish();
    }
}
