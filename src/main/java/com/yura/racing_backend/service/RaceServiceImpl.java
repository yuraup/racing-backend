package com.yura.racing_backend.service;

import com.yura.racing_backend.controller.dto.request.CardSubmitRequest;
import com.yura.racing_backend.controller.dto.request.RaceStartRequest;
import com.yura.racing_backend.controller.dto.response.RaceStatusResponse;
import com.yura.racing_backend.controller.dto.response.RoundResultResponse;
import org.springframework.stereotype.Service;

@Service
public class RaceServiceImpl implements RaceService {

    @Override
    public Long createRace(RaceStartRequest request) {
        return null;
    }

    @Override
    public void distributeCards(Long raceId) {
    }

    @Override
    public void submitCard(Long raceId, int round, CardSubmitRequest request) {
    }

    @Override
    public RoundResultResponse judgeRound(Long raceId, int round) {
        return null;
    }

    @Override
    public RaceStatusResponse getRaceStatus(Long raceId) {
        return null;
    }

    @Override
    public void finishRace(Long raceId) {
    }
}
