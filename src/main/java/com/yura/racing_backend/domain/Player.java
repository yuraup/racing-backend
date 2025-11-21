package com.yura.racing_backend.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private final Long id;
    private final String name;
    private int score;
    private final List<Integer> cards = new ArrayList<>();

    public Player(Long id, String name) {
        this.id = id;
        this.name = name;
        this.score = 0;
    }

    public Long getId() {
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
        if (!removed) {
            throw new IllegalArgumentException(
                    id + "가 보유하지 않은"+ cardNumber +"카드를 제출했습니다."
            );
        }
    }
}
