package com.yura.racing_backend.controller;

import com.yura.racing_backend.controller.dto.request.CardSubmitRequest;
import com.yura.racing_backend.controller.dto.request.RaceStartRequest;
import com.yura.racing_backend.controller.dto.response.PlayerCardsResponse;
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

    @PostMapping("/start")
    public ResponseEntity<String> createRace(@RequestBody RaceStartRequest request) {
        String raceId = raceService.createRace(request);
        return ResponseEntity.ok(raceId);
    }

    @PostMapping("/{raceId}/distribute")
    public ResponseEntity<Void> distributeCards(@PathVariable String raceId) {
        raceService.distributeCards(raceId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{raceId}/cards")
    public ResponseEntity<Void> submitCard(
            @PathVariable String raceId,
            @RequestParam("round") int round,
            @RequestBody CardSubmitRequest request
    ) {
        raceService.submitCard(raceId, round, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{raceId}/rounds/{roundNumber}/judge")
    public ResponseEntity<RoundResultResponse> judgeRound(
            @PathVariable String raceId,
            @PathVariable int roundNumber
    ) {
        RoundResultResponse response = raceService.judgeRound(raceId, roundNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{raceId}/status")
    public ResponseEntity<RaceStatusResponse> getStatus(@PathVariable String raceId) {
        RaceStatusResponse response = raceService.getRaceStatus(raceId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{raceId}/finish")
    public ResponseEntity<Void> finishRace(@PathVariable String raceId) {
        raceService.finishRace(raceId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{raceId}/player/cards")
    public ResponseEntity<PlayerCardsResponse> getPlayerCards(@PathVariable String raceId) {
        PlayerCardsResponse response = raceService.getPlayerCards(raceId);
        return ResponseEntity.ok(response);
    }
}
