package com.yura.racing_backend.util;

import java.util.concurrent.atomic.AtomicLong;

public class RaceIdGenerator {
    private static final AtomicLong counter = new AtomicLong(1);
    private RaceIdGenerator() {}

    public static Long generate() {
        return counter.getAndIncrement();
    }
}
