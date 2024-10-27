package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.PowerPlantRecord;
import ru.vsu.amm.java.enums.Type;

import java.time.LocalDate;
import java.util.Random;

public class PowerPlantRecordFactory {
    private static final int DAYS_LAST_MONTH = 30;

    private static final int DAYS_LAST_YEAR = 365;

    private static final int MAX_POWER = 100;

    private static final Type[] TYPES = Type.values();

    public static PowerPlantRecord generateRecord() {
        Random random = new Random();
        LocalDate date = LocalDate.now().minusDays(random.nextInt(DAYS_LAST_YEAR));
        Type type = TYPES[random.nextInt(TYPES.length)];
        int power = random.nextInt(MAX_POWER + 1);

        return new PowerPlantRecord(date, type, power);
    }
}