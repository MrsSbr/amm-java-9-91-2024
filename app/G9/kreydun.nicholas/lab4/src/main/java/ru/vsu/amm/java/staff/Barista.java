package ru.vsu.amm.java.staff;

import ru.vsu.amm.java.records.DrinkRecord;
import ru.vsu.amm.java.records.ListDrinks;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import java.util.logging.Logger;
import java.util.stream.Collectors;

import static ru.vsu.amm.java.generators.BaristaGenerator.generateRandomDrinkRecord;

public class Barista {

    private static final Logger logger = Logger.getLogger(Barista.class.getName());

    private List<DrinkRecord> drinkRecords;

    private final int COUNTS_ORDER = 10000;

    public Barista() {
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
        return drinkRecords
                .stream()
                .filter(record -> record.getTime().isAfter(LocalTime.of(7, 0)) && record.getTime().isBefore(LocalTime.of(9, 0)))
                .map(DrinkRecord::getDrinkName).collect(Collectors.toCollection(HashSet::new));
    }

    public long countCopCappuccino() {
        logger.info("The barista made cappuccino counting...");
        return drinkRecords
                .stream()
                .filter(drinkRecord -> Objects.equals(drinkRecord.getDrinkName(), "Cappuccino"))
                .count();
    }

    public Set<String> getDrinksNotOrderedLast3Months() {
        logger.info("Retrieving drinks not ordered in the last 3 months...");
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);

        Set<String> orderedDrinks = drinkRecords
                .stream()
                .filter(record -> record.getDate().isAfter(threeMonthsAgo))
                .map(DrinkRecord::getDrinkName)
                .collect(Collectors.toCollection(HashSet::new));


        HashSet<String> notOrderedList = new HashSet<>(ListDrinks.listDrinks);
        notOrderedList.removeAll(orderedDrinks);

        return notOrderedList;
    }

}
