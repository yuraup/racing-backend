package com.yura.racing_backend;

import com.yura.racing_backend.controller.dto.request.RaceStartRequest;
import com.yura.racing_backend.controller.dto.response.PlayerInfo;
import com.yura.racing_backend.controller.dto.response.RaceStatusResponse;
import com.yura.racing_backend.global.error.CustomException;
import com.yura.racing_backend.global.error.ErrorCode;
import com.yura.racing_backend.service.RaceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class RaceServiceImplTest {

    @Autowired
    private RaceService raceService;

    @Test
    @DisplayName("레이스 생성, 카드 분배, 상태 조회 성공")
    void createRace_and_getStatus_success() {
        // given
        RaceStartRequest request = new RaceStartRequest();
        ReflectionTestUtils.setField(request, "playerName", "yura");
        ReflectionTestUtils.setField(request, "botNumbers", 2);
        ReflectionTestUtils.setField(request, "totalRounds", 3);

        // when
        Long raceId = raceService.createRace(request);
        raceService.distributeCards(raceId);
        RaceStatusResponse status = raceService.getRaceStatus(raceId);

        // then
        assertThat(status.getTotalRounds()).isEqualTo(3);
        assertThat(status.getCurrentRound()).isEqualTo(0);
        assertThat(status.getRanking()).hasSize(1);

        assertThat(status.getRanking())
                .extracting(PlayerInfo::getName)
                .containsExactly("yura");
    }

    @Test
    @DisplayName("잘못된 raceId 조회 시 INVALID_RACE 에러 발생")
    void getStatus_invalidRaceId() {
        // given
        Long invalidRaceId = 999L;

        // when
        CustomException ex = assertThrows(
                CustomException.class,
                () -> raceService.getRaceStatus(invalidRaceId)
        );

        // then
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.INVALID_RACE);
    }
}
