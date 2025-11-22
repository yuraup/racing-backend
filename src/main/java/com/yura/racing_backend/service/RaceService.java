package com.yura.racing_backend.service;

import com.yura.racing_backend.controller.dto.request.CardSubmitRequest;
import com.yura.racing_backend.controller.dto.request.RaceStartRequest;
import com.yura.racing_backend.controller.dto.response.RaceStatusResponse;
import com.yura.racing_backend.controller.dto.response.RoundResultResponse;

public interface RaceService {

    String createRace(RaceStartRequest request);

    void distributeCards(String raceId);

    void submitCard(String raceId, int round, CardSubmitRequest request);

    RoundResultResponse judgeRound(String raceId, int round);

    RaceStatusResponse getRaceStatus(String raceId);

    void finishRace(String raceId);
}
