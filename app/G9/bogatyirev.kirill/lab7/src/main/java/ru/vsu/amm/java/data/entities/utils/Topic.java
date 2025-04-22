package ru.vsu.amm.java.data.entities.utils;

import java.util.List;
import java.util.Random;

public enum Topic {
    Nature,
    Sport,
    Games,
    Films,
    Music;

    private static final List<Topic> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Topic randomTopic() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
