package ru.vsu.amm.java.generators;

import ru.vsu.amm.java.enums.DrinkName;
import ru.vsu.amm.java.records.DrinkRecord;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import java.util.Random;

import java.util.logging.Logger;
import java.util.stream.IntStream;

import static ru.vsu.amm.java.enums.DrinkName.Espresso;
import static ru.vsu.amm.java.enums.DrinkName.Cappuccino;
import static ru.vsu.amm.java.enums.DrinkName.Americano;
import static ru.vsu.amm.java.enums.DrinkName.Latte;
import static ru.vsu.amm.java.enums.DrinkName.Matcha;
import static ru.vsu.amm.java.enums.DrinkName.Tea;

public class BaristaGenerator {

    public static final Logger log = Logger.getLogger(BaristaGenerator.class.getName());

    private static final int COUNT_DAYS = 365;
    private static final int HOUR_START = 8;
    private static final int HOUR_END = 12;
    private static final int MINUTES_IN_HOUR = 60;

    private static final DrinkName[] NAMES = {
            Espresso,
            Latte,
            Cappuccino,
            Americano,
            Matcha,
            Tea
    };

    public static DrinkRecord generateRandomDrinkRecord() {
        Random random = new Random();
        DrinkName randomDrink = NAMES[random.nextInt(NAMES.length)];
        LocalDate randomDate = LocalDate.now().minusDays(random.nextInt(COUNT_DAYS));

        int randomHour = HOUR_START + random.nextInt(HOUR_END);
        int randomMinute = random.nextInt(MINUTES_IN_HOUR);
        LocalTime randomTime = LocalTime.of(randomHour, randomMinute);

        return new DrinkRecord(randomDrink, randomDate, randomTime);
    }
    public static List<DrinkRecord> generateRandomDrinkRecord(int counts) {
        log.info("Generation...");

        return IntStream.range(0, counts)
                .mapToObj(i -> generateRandomDrinkRecord())
                .toList();
    }
}

