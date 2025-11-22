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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RaceServiceImpl implements RaceService {
    private final Map<Long, Race> raceStore = new ConcurrentHashMap<>();
    private final AtomicLong raceIdSequence = new AtomicLong(1);

    @Override
    public Long createRace(RaceStartRequest request) {
        Long raceId = raceIdSequence.getAndIncrement();
        Race race = Race.of(raceId, request);
        raceStore.put(raceId, race);
        return raceId;
    }

    private Race findRaceOrThrow(Long raceId) {
        Race race = raceStore.get(raceId);
        if (race == null) {
            throw new CustomException(ErrorCode.INVALID_RACE);
        }
        return race;
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
    public RoundResultResponse judgeRound(Long raceId, int round) {
        Race race = findRaceOrThrow(raceId);
        RoundResult result = race.judgeRound(round);
        return RoundResultResponse.from(result);
    }

    @Override
    public RaceStatusResponse getRaceStatus(Long raceId) {
        Race race = findRaceOrThrow(raceId);
        return RaceStatusResponse.from(race);
    }

    @Override
    public void finishRace(Long raceId) {
        Race race = findRaceOrThrow(raceId);
        race.finish();
    }
}
