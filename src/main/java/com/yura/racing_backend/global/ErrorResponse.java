package com.yura.racing_backend.global;

import java.time.LocalDateTime;

public class ErrorResponse {
    private final int status;
    private final String code;
    private final String message;
    private final String path;
    private final LocalDateTime timestamp;


    public ErrorResponse(int status, String code, String message, String path, LocalDateTime timestamp) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.path = path;
        this.timestamp = timestamp;
    }

    public static ErrorResponse of(int status, String code, String message, String path) {
        return new ErrorResponse(status, code, message, path, LocalDateTime.now());
    }

    public int getStatus() {
        return status;
    }
    public String getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
