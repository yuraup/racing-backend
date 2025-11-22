package com.yura.racing_backend.domain;

import com.yura.racing_backend.global.error.CustomException;
import com.yura.racing_backend.global.error.ErrorCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private final String id;
    private final String name;
    private int score;
    private final List<Integer> cards = new ArrayList<>();

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
        this.score = 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore() {
        this.score++;
    }

    public void dealCards(List<Integer> newCards) {
        cards.clear();
        cards.addAll(newCards);
    }

    public List<Integer> getCards() {
        return Collections.unmodifiableList(cards);
    }

    public boolean hasCard(int cardNumber) {
        return cards.contains(cardNumber);
    }

    public void useCard(int cardNumber) {
        boolean removed = cards.remove(Integer.valueOf(cardNumber));
        if (!removed) throw new CustomException(ErrorCode.INVALID_CARD);
    }
}
