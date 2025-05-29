package ru.vsu.amm.java.data.entities.utils;

import java.util.Random;

public enum Topic {
    Nature,
    Sport,
    Games,
    Films,
    Music;

    private static final int SIZE = Topic.values().length;
    private static final Random RANDOM = new Random();

    public static Topic randomTopic() {
        return Topic.values()[RANDOM.nextInt(SIZE)];
    }
}
