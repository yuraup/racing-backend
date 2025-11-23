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

    @PostMapping
    public ResponseEntity<Long> createRace(@RequestBody RaceStartRequest request) {
        Long raceId = raceService.createRace(request);
        return ResponseEntity.ok(raceId);
    }

    @PostMapping("/{raceId}/hands")
    public ResponseEntity<Void> distributeCards(@PathVariable Long raceId) {
        raceService.distributeCards(raceId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{raceId}/cards")
    public ResponseEntity<Void> submitCard(
            @PathVariable Long raceId,
            @RequestParam("round") int round,
            @RequestBody CardSubmitRequest request
    ) {
        raceService.submitCard(raceId, round, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{raceId}/rounds/{roundNumber}/results")
    public ResponseEntity<RoundResultResponse> judgeRound(
            @PathVariable Long raceId,
            @PathVariable int roundNumber
    ) {
        RoundResultResponse response = raceService.judgeRound(raceId, roundNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{raceId}/status")
    public ResponseEntity<RaceStatusResponse> getStatus(@PathVariable Long raceId) {
        RaceStatusResponse response = raceService.getRaceStatus(raceId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{raceId}/player/hand")
    public ResponseEntity<PlayerCardsResponse> getPlayerHand(@PathVariable Long raceId) {
        PlayerCardsResponse response = raceService.getPlayerCards(raceId);
        return ResponseEntity.ok(response);
    }
}
