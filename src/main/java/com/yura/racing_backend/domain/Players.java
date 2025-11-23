package com.yura.racing_backend.domain;

import com.yura.racing_backend.global.error.CustomException;
import com.yura.racing_backend.global.error.ErrorCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Players {

    private final List<Player> values;

    public Players(List<Player> values) {
        this.values = new ArrayList<>(values);
    }

    public Player findById(String playerId) {
        return values.stream()
                .filter(player -> player.getId().equals(playerId))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PLAYER));
    }

    public Player getSingle() {
        if (values.size() != 1) {
            throw new CustomException(ErrorCode.INVALID_PLAYER);
        }
        return values.get(0);
    }

    public List<Player> asList() {
        return Collections.unmodifiableList(values);
    }
}
