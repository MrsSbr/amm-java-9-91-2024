package ru.vsu.amm.java.generators;

import ru.vsu.amm.java.enums.DrinkName;
import ru.vsu.amm.java.records.DrinkRecord;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import java.util.Random;

import java.util.stream.IntStream;

import static ru.vsu.amm.java.enums.DrinkName.ESPRESSO;
import static ru.vsu.amm.java.enums.DrinkName.LATTE;
import static ru.vsu.amm.java.enums.DrinkName.CAPPUCCINO;
import static ru.vsu.amm.java.enums.DrinkName.AMERICANO;
import static ru.vsu.amm.java.enums.DrinkName.MATCHA;
import static ru.vsu.amm.java.enums.DrinkName.TEA;

import static ru.vsu.amm.java.service.Logg.logger;

public class BaristaGenerator {

    private static final int COUNT_DAYS = 365;
    private static final int HOUR_START = 8;
    private static final int HOUR_END = 12;
    private static final int MINUTES_IN_HOUR = 60;

    private static final DrinkName[] NAMES = {
            ESPRESSO,
            LATTE,
            CAPPUCCINO,
            AMERICANO,
            MATCHA,
            TEA
    };

    public static DrinkRecord generateRandomDrinkRecord() {
        Random random = new Random();
        DrinkName randomDrink = NAMES[random.nextInt(NAMES.length)];
        LocalDate randomDate = LocalDate.now().minusDays(random.nextInt(COUNT_DAYS));

        int randomHour = HOUR_START + random.nextInt(HOUR_END);
        int randomMinute = random.nextInt(MINUTES_IN_HOUR);
        LocalTime randomTime = LocalTime.of(randomHour, randomMinute);

        logger.fine("Generate elem");

        return new DrinkRecord(randomDrink, randomDate, randomTime);
    }
    public static List<DrinkRecord> generateRandomDrinkRecord(int counts) {
        logger.fine("Generation");

        return IntStream.range(0, counts)
                .mapToObj(i -> generateRandomDrinkRecord())
                .toList();
    }
}

