package com.yura.racing_backend.domain;

public class Player {
    private final Long id;
    private final String name;
    private int score;

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
}
