package ru.vsu.amm.java.service;

import ru.vsu.amm.java.enums.DrinkName;
import ru.vsu.amm.java.records.ListDrinks;
import ru.vsu.amm.java.staff.BaristaStorage;
import ru.vsu.amm.java.records.DrinkRecord;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.vsu.amm.java.service.Logg.logger;
import static ru.vsu.amm.java.staff.BaristaStorage.END_TIME;
import static ru.vsu.amm.java.staff.BaristaStorage.START_TIME;

public class DrinkRecordAnalyzer {

    private final BaristaStorage baristaStorage;

    public DrinkRecordAnalyzer(BaristaStorage baristaStorage) {
        this.baristaStorage = baristaStorage;
    }

    public Set<String> getMorningDrinks() {
        logger.info("Retrieving morning drinks (7:00 - 9:00)...");
        return baristaStorage.getRecords()
                .stream()
                .filter(record -> record.getTime().isAfter(START_TIME) && record.getTime().isBefore(END_TIME))
                .map(DrinkRecord::getDrinkName).collect(Collectors.toSet());
    }

    public long countCopCappuccino() {
        logger.info("The barista made cappuccino counting...");
        return baristaStorage.getRecords()
                .stream()
                .filter(drinkRecord -> Objects.equals(drinkRecord.getDrinkName(), DrinkName.CAPPUCCINO.name()))
                .count();
    }

    public Set<String> getDrinksNotOrderedLast3Months() {
        logger.info("Retrieving drinks not ordered in the last 3 months...");
        final int COUNT_MONTHS = 3;
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(COUNT_MONTHS);

        Set<String> orderedDrinks = baristaStorage.getRecords()
                .stream()
                .filter(record -> record.getDate().isAfter(threeMonthsAgo))
                .map(DrinkRecord::getDrinkName)
                .collect(Collectors.toSet());
        Set<String> notOrderedList = new HashSet<>(ListDrinks.LIST_DRINKS);
        notOrderedList.removeAll(orderedDrinks);

        return notOrderedList;
    }
}