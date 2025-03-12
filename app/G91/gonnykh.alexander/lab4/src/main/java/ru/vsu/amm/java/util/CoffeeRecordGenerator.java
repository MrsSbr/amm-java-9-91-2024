package ru.vsu.amm.java.util;

import ru.vsu.amm.java.enums.CoffeeType;
import ru.vsu.amm.java.records.CoffeeRecord;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CoffeeRecordGenerator {


    public static List<CoffeeRecord> generateCoffeeRecords(int count) {
        final Random random = new Random();
        List<CoffeeRecord> records = new ArrayList<CoffeeRecord>();

        for (int i = 0; i < count; i++) {
            CoffeeType name = CoffeeType.values()[random.nextInt(CoffeeType.values().length)];
            long preparationTime = random.nextInt(15) + 1;
            float cost = 2 + random.nextFloat(8);
            cost = new BigDecimal(cost)
                    .setScale(2, RoundingMode.HALF_UP)
                    .floatValue();

            records.add(new CoffeeRecord(name, getRandomDateTime(), preparationTime, cost));
        }
        return records;
    }

    private static LocalDateTime getRandomDateTime() {
        final Random random = new Random();
        int day = random.nextInt(28) + 1;
        int month = random.nextInt(12) + 1;
        int year = random.nextInt(100) + 1900;
        int hour = random.nextInt(24);
        int minute = random.nextInt(60);
        int second = random.nextInt(60);

        return LocalDateTime.of(year, month, day, hour, minute, second);
    }
}
