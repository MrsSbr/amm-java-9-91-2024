package ru.vsu.amm.java.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.vsu.amm.java.enums.DrinkName;
import ru.vsu.amm.java.records.DrinkRecord;
import ru.vsu.amm.java.staff.BaristaStorage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class BaristaTest {
    private BaristaStorage baristaStorage;
    private DrinkRecordAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        baristaStorage = new BaristaStorage();
        analyzer = new DrinkRecordAnalyzer(baristaStorage);
    }

    @Test
    void testEmptyGetSet() {
        baristaStorage.setRecords(new ArrayList<>());

        assertTrue(baristaStorage.getRecords().isEmpty());

        logger.info("test empty get set end");
    }

    @Test
    void testNotEmptyGetSet() {
        List<DrinkRecord> drinkRecords = new ArrayList<>();
        drinkRecords.add(new DrinkRecord(DrinkName.Espresso, LocalDate.now(), LocalTime.of(6, 59)));
        drinkRecords.add(new DrinkRecord(DrinkName.Latte, LocalDate.now(), LocalTime.of(9, 1)));
        baristaStorage.setRecords(drinkRecords);

        assertTrue(baristaStorage.getRecords().contains(drinkRecords.get(0)));
        assertTrue(baristaStorage.getRecords().contains(drinkRecords.get(1)));
        assertFalse(baristaStorage.getRecords().isEmpty());

        logger.info("test not empty get set end");
    }

    @Test
    void testGetMorningDrinks() {
        List<DrinkRecord> drinkRecords = new ArrayList<>();
        drinkRecords.add(new DrinkRecord(DrinkName.Espresso, LocalDate.now(), LocalTime.of(6, 59)));
        drinkRecords.add(new DrinkRecord(DrinkName.Cappuccino, LocalDate.now(), LocalTime.of(8, 0)));
        drinkRecords.add(new DrinkRecord(DrinkName.Latte, LocalDate.now(), LocalTime.of(9, 1)));

        baristaStorage.setRecords(drinkRecords);
        Set<String> morningDrinks = analyzer.getMorningDrinks();

        assertEquals(1, morningDrinks.size());
        assertTrue(morningDrinks.contains("Cappuccino"));

        logger.info("test get morning drinks end");
    }

    @Test
    void testGetMorningDrinksEmpty() {
        List<DrinkRecord> drinkRecords = new ArrayList<>();
        drinkRecords.add(new DrinkRecord(DrinkName.Espresso, LocalDate.now(), LocalTime.of(10, 0)));
        drinkRecords.add(new DrinkRecord(DrinkName.Cappuccino, LocalDate.now(), LocalTime.of(11, 0)));
        drinkRecords.add(new DrinkRecord(DrinkName.Latte, LocalDate.now(), LocalTime.of(12, 0)));
        baristaStorage.setRecords(drinkRecords);
        Set<String> morningDrinks = analyzer.getMorningDrinks();

        assertEquals(0, morningDrinks.size());

        logger.info("test get morning drinks empty end");
    }

    @Test
    void testCountCopCappuccino() {
        List<DrinkRecord> drinkRecords = new ArrayList<>();
        drinkRecords.add(new DrinkRecord(DrinkName.Cappuccino, LocalDate.now(), LocalTime.of(8, 0)));
        drinkRecords.add(new DrinkRecord(DrinkName.Cappuccino, LocalDate.now(), LocalTime.of(9, 0)));
        drinkRecords.add(new DrinkRecord(DrinkName.Latte, LocalDate.now(), LocalTime.of(10, 0)));

        baristaStorage.setRecords(drinkRecords);

        long cappuccinoCount = analyzer.countCopCappuccino();

        assertEquals(2, cappuccinoCount);

        logger.info("test count cappuccino end");
    }

    @Test
    void testCountCopCappuccinoZero() {
        List<DrinkRecord> drinkRecords = new ArrayList<>();
        drinkRecords.add(new DrinkRecord(DrinkName.Latte, LocalDate.now(), LocalTime.of(8, 0)));
        drinkRecords.add(new DrinkRecord(DrinkName.Espresso, LocalDate.now(), LocalTime.of(9, 0)));
        drinkRecords.add(new DrinkRecord(DrinkName.Americano, LocalDate.now(), LocalTime.of(10, 0)));

        baristaStorage.setRecords(drinkRecords);

        long cappuccinoCount = analyzer.countCopCappuccino();

        assertEquals(0, cappuccinoCount);

        logger.info("test count cappuccino null end");
    }

    @Test
    void testGetDrinksNotOrderedLast3Months() {
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        List<DrinkRecord> drinkRecords = new ArrayList<>();
        drinkRecords.add(new DrinkRecord(DrinkName.Cappuccino, threeMonthsAgo.minusDays(1), LocalTime.of(8, 0)));
        drinkRecords.add(new DrinkRecord(DrinkName.Latte, LocalDate.now(), LocalTime.of(9, 0)));

        baristaStorage.setRecords(drinkRecords);

        Set<String> notOrderedDrinks = analyzer.getDrinksNotOrderedLast3Months();

        assertTrue(notOrderedDrinks.contains("Espresso"));
        assertTrue(notOrderedDrinks.contains("Americano"));
        assertTrue(notOrderedDrinks.contains("Latte"));
        assertFalse(notOrderedDrinks.contains("Cappuccino"));

        logger.info("test get drinks not ordered last 3 months end");
    }
}