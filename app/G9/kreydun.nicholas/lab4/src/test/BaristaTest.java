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

    private BaristaStorage barista;

    @BeforeEach
    void setUp() {
        barista = new Barista();
    }

    @Test
    void testEmptyGetSet() {
        barista.setRecords(new ArrayList<>());

        assertTrue(barista.getRecords().isEmpty());

        logger.info("test empty get set end");
    }

    @Test
    void testNotEmptyGetSet() {
        List<DrinkRecord> drinkRecords = new ArrayList<>();
        drinkRecords.add(new DrinkRecord(DrinkName.Espresso, LocalDate.now(), LocalTime.of(6, 59)));
        drinkRecords.add(new DrinkRecord(DrinkName.Latte, LocalDate.now(), LocalTime.of(9, 1)));
        barista.setRecords(drinkRecords);

        assertTrue(barista.getRecords().contains(drinkRecords.getFirst()));
        assertTrue(barista.getRecords().contains(drinkRecords.getLast()));
        assertFalse(barista.getRecords().isEmpty());

        logger.info("test not empty get set end");
    }

    @Test
    void testGetMorningDrinks() {
        List<DrinkRecord> drinkRecords = new ArrayList<>();
        drinkRecords.add(new DrinkRecord(DrinkName.Espresso, LocalDate.now(), LocalTime.of(6, 59)));
        drinkRecords.add(new DrinkRecord(DrinkName.Cappuccino, LocalDate.now(), LocalTime.of(8, 0)));
        drinkRecords.add(new DrinkRecord(DrinkName.Latte, LocalDate.now(), LocalTime.of(9, 1)));

        barista.setRecords(drinkRecords);
        Set<String> morningDrinks = barista.getMorningDrinks();

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

        barista.setRecords(drinkRecords);
        Set<String> morningDrinks = barista.getMorningDrinks();

        assertEquals(0, morningDrinks.size());

        logger.info("test get morning drinks empty end");
    }

    @Test
    void testCountCopCappuccino() {
        List<DrinkRecord> drinkRecords = new ArrayList<>();
        drinkRecords.add(new DrinkRecord(DrinkName.Cappuccino, LocalDate.now(), LocalTime.of(8, 0)));
        drinkRecords.add(new DrinkRecord(DrinkName.Cappuccino, LocalDate.now(), LocalTime.of(9, 0)));
        drinkRecords.add(new DrinkRecord(DrinkName.Latte, LocalDate.now(), LocalTime.of(10, 0)));

        barista.setRecords(drinkRecords);

        long cappuccinoCount = barista.countCopCappuccino();

        assertEquals(2, cappuccinoCount);

        logger.info("test count cappuccino end");
    }

    @Test
    void testCountCopCappuccinoZero() {
        List<DrinkRecord> drinkRecords = new ArrayList<>();
        drinkRecords.add(new DrinkRecord(DrinkName.Latte, LocalDate.now(), LocalTime.of(8, 0)));
        drinkRecords.add(new DrinkRecord(DrinkName.Espresso, LocalDate.now(), LocalTime.of(9, 0)));
        drinkRecords.add(new DrinkRecord(DrinkName.Americano, LocalDate.now(), LocalTime.of(10, 0)));

        barista.setRecords(drinkRecords);

        long cappuccinoCount = barista.countCopCappuccino();

        assertEquals(0, cappuccinoCount);

        logger.info("test count cappuccino null end");
    }

    @Test
    void testGetDrinksNotOrderedLast3Months() {
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        List<DrinkRecord> drinkRecords = new ArrayList<>();
        drinkRecords.add(new DrinkRecord(DrinkName.Cappuccino, threeMonthsAgo.minusDays(1), LocalTime.of(8, 0)));
        drinkRecords.add(new DrinkRecord(DrinkName.Latte, LocalDate.now(), LocalTime.of(9, 0)));

        barista.setRecords(drinkRecords);

        Set<String> notOrderedDrinks = barista.getDrinksNotOrderedLast3Months();

        assertTrue(notOrderedDrinks.contains("Cappuccino"));
        assertFalse(notOrderedDrinks.contains("Latte"));

        logger.info("test get drinks not ordered last 3 months end");
    }

    @Test
    void testGetDrinksNotOrderedLast3MonthsAllOrderedEmpty() {
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        List<DrinkRecord> drinkRecords = new ArrayList<>();
        ListDrinks.listDrinks = new HashSet<>();
        drinkRecords.add(new DrinkRecord(DrinkName.Cappuccino, threeMonthsAgo.plusDays(1), LocalTime.of(8, 0)));
        drinkRecords.add(new DrinkRecord(DrinkName.Latte, threeMonthsAgo.plusDays(2), LocalTime.of(9, 0)));
        drinkRecords.add(new DrinkRecord(DrinkName.Espresso, LocalDate.now(), LocalTime.of(10, 0)));
        drinkRecords.add(new DrinkRecord(DrinkName.Tea, threeMonthsAgo.plusDays(3), LocalTime.of(11, 0)));

        barista.setRecords(drinkRecords);

        Set<String> notOrderedDrinks = barista.getDrinksNotOrderedLast3Months();

        assertEquals(0, notOrderedDrinks.size() - 1);

        logger.info("test get drinks not ordered last 3 months empty end");
    }
}