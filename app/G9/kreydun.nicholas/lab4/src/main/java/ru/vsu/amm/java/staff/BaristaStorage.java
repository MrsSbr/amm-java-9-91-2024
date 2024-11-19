package ru.vsu.amm.java.staff;

import ru.vsu.amm.java.records.DrinkRecord;

import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

import static ru.vsu.amm.java.generators.BaristaGenerator.generateRandomDrinkRecord;
import static ru.vsu.amm.java.service.Logg.logger;

public class BaristaStorage {
    private List<DrinkRecord> drinkRecords;

    private static final int COUNTS_ORDER = 1000;
    private static final int START_HOUR = 7;
    private static final int END_HOUR = 9;
    private static final int MINUTES = 0;
    public static final LocalTime START_TIME = LocalTime.of(START_HOUR, MINUTES);
    public static final LocalTime END_TIME = LocalTime.of(END_HOUR, MINUTES);

    public BaristaStorage() {
        drinkRecords = new ArrayList<>();
        logger.info("Created Barista");
    }

    public void generateRandomDrinkRecords() {
        this.drinkRecords = generateRandomDrinkRecord(COUNTS_ORDER);
        logger.info("Generated  drink records " + drinkRecords.size());
    }

    public void setRecords(List<DrinkRecord> drinkRecords) {
        this.drinkRecords = drinkRecords;
    }

    public List<DrinkRecord> getRecords() {
        return drinkRecords;
    }
}