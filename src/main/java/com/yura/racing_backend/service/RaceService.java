package com.yura.racing_backend.service;

import com.yura.racing_backend.controller.dto.request.CardSubmitRequest;
import com.yura.racing_backend.controller.dto.request.RaceStartRequest;
import com.yura.racing_backend.controller.dto.response.RaceStatusResponse;
import com.yura.racing_backend.controller.dto.response.RoundResultResponse;

public interface RaceService {

    Long createRace(RaceStartRequest request);

    void distributeCards(Long raceId);

    void submitCard(Long raceId, int round, CardSubmitRequest request);

    RoundResultResponse judgeRound(Long raceId, int round);

    RaceStatusResponse getRaceStatus(Long raceId);

    void finishRace(Long raceId);
}
