package ru.vsu.amm.java.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.vsu.amm.java.generate.RandomWinnerGenerator;
import ru.vsu.amm.java.winners.StorageWinners;
import ru.vsu.amm.java.winners.Winner;


import java.util.List;

public class StorageWinnersTest {

    private StorageWinners<Winner> storage;

    @BeforeEach
    public void setUp() {
        storage = new StorageWinners<>();
        RandomWinnerGenerator.populateStorageWinners(storage, 10); // Заполняем хранилище случайными победителями
    }

    @Test
    public void testAddAndListWinners() {
        Winner winner = new Winner(2020, "TestName", "IT");
        storage.add(winner);

        List<String> winners = storage.listWinners();
        assertTrue(winners.contains("TestName"), "Winner should be added.");
    }

    @Test
    public void testFindMostFrequentDepartment() {
        List<String> mostFrequentDepartments = storage.findMostFrequentDepartment();
        assertNotNull(mostFrequentDepartments, "Most frequent departments should not be null.");
        assertFalse(mostFrequentDepartments.isEmpty(), "Should contain at least one department.");
    }

    @Test
    public void testCountOneTimeWinners() {
        int count = storage.countOneTimeWinners();
        assertTrue(count >= 0, "Count of one-time winners should be non-negative.");
    }

    @Test
    public void testPrintFindMostFrequentDepartment() {
        assertDoesNotThrow(() -> storage.printFindMostFrequentDepartment(), "Should not throw an exception while printing.");
    }

    @Test
    public void testPrintWinners() {
        assertDoesNotThrow(() -> storage.printWinners(), "Should not throw an exception while printing winners.");
    }
}
