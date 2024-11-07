package ru.vsu.amm.java.staff;

import ru.vsu.amm.java.enums.DrinkName;
import ru.vsu.amm.java.records.DrinkRecord;
import ru.vsu.amm.java.records.ListDrinks;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import java.util.stream.Collectors;

import static ru.vsu.amm.java.generators.BaristaGenerator.generateRandomDrinkRecord;
import static ru.vsu.amm.java.service.Logg.logger;

public class BaristaStorage {

    private List<DrinkRecord> drinkRecords;

    private final int COUNTS_ORDER = 1000;

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

    public Set<String> getMorningDrinks() {
        logger.info("Retrieving morning drinks (7:00 - 9:00)...");
        final int START_HOUR = 7;
        final int END_HOUR = 9;
        final int MINUTES = 0;
        return drinkRecords
                .stream()
                .filter(record -> record.getTime().isAfter(LocalTime.of(START_HOUR, MINUTES)) && record.getTime().isBefore(LocalTime.of(END_HOUR, MINUTES)))
                .map(DrinkRecord::getDrinkName).collect(Collectors.toSet());
    }

    public long countCopCappuccino() {
        logger.info("The barista made cappuccino counting...");
        return drinkRecords
                .stream()
                .filter(drinkRecord -> Objects.equals(drinkRecord.getDrinkName(), DrinkName.CAPPUCCINO.name()))
                .count();
    }

    public Set<String> getDrinksNotOrderedLast3Months() {
        logger.info("Retrieving drinks not ordered in the last 3 months...");
        final int COUNT_MONTHS = 3;
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(COUNT_MONTHS);

        Set<String> orderedDrinks = drinkRecords
                .stream()
                .filter(record -> record.getDate().isAfter(threeMonthsAgo))
                .map(DrinkRecord::getDrinkName)
                .collect(Collectors.toSet());


        Set<String> notOrderedList = new HashSet<>(ListDrinks.listDrinks);
        notOrderedList.removeAll(orderedDrinks);

        return notOrderedList;
    }

}
