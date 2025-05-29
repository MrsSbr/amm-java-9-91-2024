package ru.vsu.amm.java.data.entities.utils;

import java.util.List;
import java.util.Random;

public enum Difficulty {
    LIGHT,
    MEDIUM,
    HARD;

    private static final List<Difficulty> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Difficulty randomDifficulty() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
