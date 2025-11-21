package com.yura.racing_backend;

import com.yura.racing_backend.controller.dto.request.RaceStartRequest;
import com.yura.racing_backend.controller.dto.response.PlayerInfo;
import com.yura.racing_backend.controller.dto.response.RaceStatusResponse;
import com.yura.racing_backend.global.error.CustomException;
import com.yura.racing_backend.global.error.ErrorCode;
import com.yura.racing_backend.service.RaceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class RaceServiceImplTest {

    @Autowired
    private RaceService raceService;

    @Test
    @DisplayName("레이스 생성, 카드 분배, 상태 조회")
    void createRace_and_getStatus() {
        // given
        RaceStartRequest request = new RaceStartRequest();
        ReflectionTestUtils.setField(request, "playerNames", List.of("yura", "uteco"));
        ReflectionTestUtils.setField(request, "botEnabled", true);
        ReflectionTestUtils.setField(request, "totalRounds", 3);

        // when
        Long raceId = raceService.createRace(request);
        raceService.distributeCards(raceId);
        RaceStatusResponse status = raceService.getRaceStatus(raceId);

        // then
        assertThat(status.getTotalRounds()).isEqualTo(3);
        assertThat(status.getCurrentRound()).isEqualTo(0);

        assertThat(status.getRanking()).hasSize(2);

        assertThat(status.getRanking())
                .extracting(PlayerInfo::getName)
                .containsExactlyInAnyOrder("yura", "uteco");
    }


    @Test
    @DisplayName("존재하지 않는 레이스 ID로 조회 시 에러 발생")
    void getStatus_withInvalidRaceId() {
        //given
        Long raceId = 999L;
        //when
        CustomException ex = assertThrows(CustomException.class,
                () -> raceService.getRaceStatus(raceId));
        //then
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.INVALID_RACE);
    }
}
