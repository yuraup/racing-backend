package com.yura.racing_backend.controller;

import com.yura.racing_backend.controller.dto.request.CardSubmitRequest;
import com.yura.racing_backend.controller.dto.request.RaceStartRequest;
import com.yura.racing_backend.controller.dto.response.RaceStatusResponse;
import com.yura.racing_backend.controller.dto.response.RoundResultResponse;
import com.yura.racing_backend.service.RaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/races")
public class RaceController {

    private final RaceService raceService;

    public RaceController(RaceService raceService) {
        this.raceService = raceService;
    }

    // 레이스 생성
    @PostMapping("/start")
    public ResponseEntity<Long> createRace(@RequestBody RaceStartRequest request) {
        Long raceId = raceService.createRace(request);
        return ResponseEntity.ok(raceId);
    }

    // 카드 분배
    @PostMapping("/{raceId}/distribute")
    public ResponseEntity<Void> distributeCards(@PathVariable Long raceId) {
        raceService.distributeCards(raceId);
        return ResponseEntity.ok().build();
    }

    // 카드 제출
    @PostMapping("/{raceId}/cards")
    public ResponseEntity<Void> submitCard(
            @PathVariable Long raceId,
            @RequestParam("round") int round,
            @RequestBody CardSubmitRequest request
    ) {
        raceService.submitCard(raceId, round, request);
        return ResponseEntity.ok().build();
    }

    // 라운드 판정
    @PostMapping("/{raceId}/rounds/{roundNumber}/judge")
    public ResponseEntity<RoundResultResponse> judgeRound(
            @PathVariable Long raceId,
            @PathVariable int roundNumber
    ) {
        RoundResultResponse response = raceService.judgeRound(raceId, roundNumber);
        return ResponseEntity.ok(response);
    }

    // 레이스 상태 조회
    @GetMapping("/{raceId}/status")
    public ResponseEntity<RaceStatusResponse> getStatus(@PathVariable Long raceId) {
        RaceStatusResponse response = raceService.getRaceStatus(raceId);
        return ResponseEntity.ok(response);
    }

    // 레이스 종료
    @PostMapping("/{raceId}/finish")
    public ResponseEntity<Void> finishRace(@PathVariable Long raceId) {
        raceService.finishRace(raceId);
        return ResponseEntity.ok().build();
    }
}
