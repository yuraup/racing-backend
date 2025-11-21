package com.yura.racing_backend.global.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // 400
    INVALID_ROUND(HttpStatus.BAD_REQUEST, "유효하지 않은 라운드 번호입니다."),
    INVALID_CARD(HttpStatus.BAD_REQUEST, "보유하지 않은 카드를 제출했습니다."),
    INVALID_PLAYER(HttpStatus.BAD_REQUEST, "존재하지 않는 플레이어입니다."),
    INVALID_RACE(HttpStatus.BAD_REQUEST, "존재하지 않는 레이스입니다."),

    // 409
    ROUND_NOT_READY(HttpStatus.CONFLICT, "아직 카드를 제출하지 않은 라운드입니다."),
    RACE_ALREADY_FINISHED(HttpStatus.CONFLICT, "이미 종료된 레이스입니다."),

    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
