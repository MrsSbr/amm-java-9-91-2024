package ru.vsu.amm.java.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.enums.CoffeeType;
import ru.vsu.amm.java.records.CoffeeRecord;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CoffeeServiceTest {
    private List<CoffeeRecord> coffeeRecords;

    @BeforeEach
    public void setUp() {
        coffeeRecords = new ArrayList<>();
    }

    @Test
    @DisplayName("calculate average preparation time (empty)")
    void calculateAveragePreparationTimeEmpty() {

        Map<CoffeeType, Double> actual = CoffeeService.calculateAveragePreparationTime(coffeeRecords);
        assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("calculate average preparation time (one value)")
    void calculateAveragePreparationTimeOneValue() {
        coffeeRecords.add(new CoffeeRecord(CoffeeType.CAPPUCCINO
                , LocalDateTime.now(), 5, 3.0));

        Map<CoffeeType, Double> expected = Map.of(CoffeeType.CAPPUCCINO, 5.0);

        Map<CoffeeType, Double> actual = CoffeeService.calculateAveragePreparationTime(coffeeRecords);
        assertTrue(expected.containsKey(CoffeeType.CAPPUCCINO)
                && expected.get(CoffeeType.CAPPUCCINO).equals(5.0));
        assertEquals(actual.size(), expected.size());
    }

    @Test
    @DisplayName("calculate average preparation time (multiple values)")
    void calculateAveragePreparationTimeMultipleValues() {
        coffeeRecords.add(new CoffeeRecord(CoffeeType.CAPPUCCINO
                , LocalDateTime.now(), 5, 3.0));

        coffeeRecords.add(new CoffeeRecord(CoffeeType.CAPPUCCINO
                , LocalDateTime.now(), 7, 3.0));

        Map<CoffeeType, Double> expected = Map.of(CoffeeType.CAPPUCCINO, 6.0);

        Map<CoffeeType, Double> actual = CoffeeService.calculateAveragePreparationTime(coffeeRecords);
        assertTrue(expected.containsKey(CoffeeType.CAPPUCCINO)
                && expected.containsValue(6.0));
        assertEquals(actual.size(), expected.size());
    }

    @Test
    @DisplayName("find busiest hour (empty)")
    void findBusiestHourEmpty() {
        Optional<Integer> actual = CoffeeService.findBusiestHour(coffeeRecords);
        assertTrue(actual.isEmpty());

    }

    @Test
    @DisplayName("find busiest hour (one value)")
    void findBusiestHourOneValue() {
        coffeeRecords.add(new CoffeeRecord(CoffeeType.CAPPUCCINO
                , LocalDateTime.of(2024, 11, 21, 10, 0)
                , 5, 3.0));

        Optional<Integer> expected = Optional.of(10);

        Optional<Integer> actual = CoffeeService.findBusiestHour(coffeeRecords);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("find busiest hour (multiple values)")
    void findBusiestHourMultipleValues() {
        coffeeRecords.add(new CoffeeRecord(CoffeeType.CAPPUCCINO
                , LocalDateTime.of(2024, 11, 21, 10, 0)
                , 5, 3.0));

        coffeeRecords.add(new CoffeeRecord(CoffeeType.LATTE
                , LocalDateTime.of(2024, 11, 21, 10, 30)
                , 5, 3.5));

        Optional<Integer> expected = Optional.of(10);

        Optional<Integer> actual = CoffeeService.findBusiestHour(coffeeRecords);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("find coffee ordered from 7 to 12 (empty)")
    void findCoffeeOrderedFrom7To12Empty() {
        List<CoffeeType> expected = List.of();

        List<CoffeeType> actual = CoffeeService.findCoffeeOrderedFrom7To12(coffeeRecords);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("find coffee ordered from 7 to 12 (one value)")
    void findCoffeeOrderedFrom7To12OneValue() {
        coffeeRecords.add(new CoffeeRecord(CoffeeType.CAPPUCCINO
                , LocalDateTime.of(2024, 11, 21, 8, 0)
                , 5, 3.0));

        List<CoffeeType> expected = List.of(CoffeeType.CAPPUCCINO);

        List<CoffeeType> actual = CoffeeService.findCoffeeOrderedFrom7To12(coffeeRecords);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("find coffee ordered from 7 to 12 (multiple values)")
    void findCoffeeOrderedFrom7To12MultipleValues() {
        coffeeRecords.add(new CoffeeRecord(CoffeeType.CAPPUCCINO
                , LocalDateTime.of(2024, 11, 21, 8, 0),
                5, 3.0));

        coffeeRecords.add(new CoffeeRecord(CoffeeType.LATTE
                , LocalDateTime.of(2024, 11, 21, 9, 0)
                , 5, 3.5));

        List<CoffeeType> expected = List.of(CoffeeType.LATTE, CoffeeType.CAPPUCCINO);

        List<CoffeeType> actual = CoffeeService.findCoffeeOrderedFrom7To12(coffeeRecords);
        assertTrue(expected.containsAll(actual));
        assertTrue(actual.containsAll(expected));
    }

    @Test
    @DisplayName("find best coffee ratio (empty)")
    void findBestCoffeeRatioEmpty() {
        Optional<CoffeeType> actual = CoffeeService.findBestCoffeeRatio(coffeeRecords);
        assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("find best coffee ratio (one value)")
    void findBestCoffeeRatioOneValue() {
        coffeeRecords.add(new CoffeeRecord(CoffeeType.CAPPUCCINO
                , LocalDateTime.now(), 5, 3.0));

        Optional<CoffeeType> expected = Optional.of(CoffeeType.CAPPUCCINO);

        Optional<CoffeeType> actual = CoffeeService.findBestCoffeeRatio(coffeeRecords);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("find best coffee ratio (multiple values)")
    void findBestCoffeeRatioMultipleValues() {
        coffeeRecords.add(new CoffeeRecord(CoffeeType.CAPPUCCINO
                , LocalDateTime.now(), 5, 5.0));

        coffeeRecords.add(new CoffeeRecord(CoffeeType.LATTE
                , LocalDateTime.now(), 7, 3.5));

        Optional<CoffeeType> expected = Optional.of(CoffeeType.LATTE);

        Optional<CoffeeType> actual = CoffeeService.findBestCoffeeRatio(coffeeRecords);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("find best coffee ratio (preparation time is 0)")
    void findBestCoffeeRatioMultipleValue() {
        coffeeRecords.add(new CoffeeRecord(CoffeeType.CAPPUCCINO
                , LocalDateTime.now(), 0, 5.0));

        coffeeRecords.add(new CoffeeRecord(CoffeeType.LATTE
                , LocalDateTime.now(), 7, 3.5));

        Optional<CoffeeType> actual = CoffeeService.findBestCoffeeRatio(coffeeRecords);
        assertTrue(actual.isEmpty());
    }
}