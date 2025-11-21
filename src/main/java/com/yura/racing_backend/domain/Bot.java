package com.yura.racing_backend.domain;

import com.yura.racing_backend.global.error.CustomException;
import com.yura.racing_backend.global.error.ErrorCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bot {
    private final String name = "BOT";
    private final List<Integer> cards = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void dealCards(List<Integer> newCards) {
        cards.clear();
        cards.addAll(newCards);
    }

    public List<Integer> getCards(){
        return Collections.unmodifiableList(cards);
    }

    public int getCardForRound(int roundNumber) {
        if (roundNumber < 1 || roundNumber > cards.size()) {
            throw new CustomException(ErrorCode.INVALID_ROUND);
        }
        return cards.get(roundNumber - 1);
    }
}
