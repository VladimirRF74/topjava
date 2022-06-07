package ru.javawebinar.topjava.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IdCounter {
    private static final AtomicInteger idCounter = new AtomicInteger(1);

    public static Integer createID() {
        return idCounter.getAndIncrement();
    }
}
