package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.PowerPlant;
import ru.vsu.amm.java.enums.Type;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PowerPlantFactory {
    private final static Random RANDOM = new Random();
    private final static Type[] TYPES = Type.values();
    private final static int NUMBER_OF_RECENT_YEARS = 2;
    private final static int MAX_POWER = 100;

    public static List<PowerPlant> generatePowerPlants(int count) {
        List<PowerPlant> powerPlants = new ArrayList<>();
        LocalDate startDate = LocalDate.now().minusYears(NUMBER_OF_RECENT_YEARS);
        LocalDate endDate = LocalDate.now();

        for (int i = 0; i < count; ++i) {
            LocalDate randomDate = getRandomDate(startDate, endDate);
            Type randomType = getRandomType();
            int randomPower = RANDOM.nextInt(MAX_POWER + 1);

            powerPlants.add(new PowerPlant(randomDate, randomType, randomPower));
        }

        return powerPlants;
    }

    public static LocalDate getRandomDate(LocalDate startDate, LocalDate endDate) {
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        long randomDays = RANDOM.nextInt((int) daysBetween + 1);

        return startDate.plusDays(randomDays);
    }

    private static Type getRandomType() {
        return TYPES[RANDOM.nextInt(TYPES.length)];
    }
}