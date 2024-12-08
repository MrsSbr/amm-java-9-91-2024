package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.Family;
import ru.vsu.amm.java.entity.FlightData;

import java.util.Random;

public class GenerateFamilyUtil {
    private static final Random rand = new Random();

    public static Family generateFamily() {
        return new Family(rand.nextInt(FlightData.MAX_NUMBER_FLIGHT) + 1, rand.nextInt(1, 6));
    }
}
