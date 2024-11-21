package ru.vsu.amm.java;

import java.util.Random;

public class RandomFamilyGenerator {
    private static final int FLIGHT_MIN = 1;
    private static final int FLIGHT_MAX = 11;
    private static final int FAMILY_MIN = 1;
    private static final int FAMILY_MAX = 9;

    public static FamilyRecord generateRandomFamily() {
        Random random = new Random();
        int flightNumber = random.nextInt(FLIGHT_MIN, FLIGHT_MAX);
        int familyAmount = random.nextInt(FAMILY_MIN, FAMILY_MAX);
        return new FamilyRecord(flightNumber, familyAmount);
    }
}
