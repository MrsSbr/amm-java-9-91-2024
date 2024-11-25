package ru.vsu.amm.java;

import java.util.Random;

public enum DetailTypes {
    BOLT,
    SCREW,
    SHEET,
    SPRING;

    public static DetailTypes getRandomType() {
        Random random = new Random();
        return  DetailTypes.values()[random.nextInt(DetailTypes.values().length)];
    }
}
