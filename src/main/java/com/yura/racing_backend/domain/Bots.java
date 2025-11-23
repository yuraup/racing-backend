package com.yura.racing_backend.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bots {

    private final List<Bot> values;

    public Bots(List<Bot> values) {
        this.values = new ArrayList<>(values);
    }

    public List<Bot> asList() {
        return Collections.unmodifiableList(values);
    }
}
