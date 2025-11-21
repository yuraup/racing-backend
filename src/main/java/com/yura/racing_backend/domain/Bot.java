package com.yura.racing_backend.domain;

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
            throw new IllegalArgumentException("봇에게 해당 라운드의 카드가 없습니다. round=" + roundNumber);
        }
        return cards.get(roundNumber - 1);
    }
}
