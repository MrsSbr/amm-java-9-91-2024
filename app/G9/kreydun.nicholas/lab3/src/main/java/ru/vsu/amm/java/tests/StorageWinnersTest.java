package ru.vsu.amm.java.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.winners.StorageWinners;
import ru.vsu.amm.java.winners.Winner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class StorageWinnersTest {
    private StorageWinners<Winner> storage;

    @BeforeEach
    public void setUp() {
        storage = new StorageWinners<>();
        storage.add(new Winner(2020, "John Doe", "IT"));
        storage.add(new Winner(2021, "Jane Smith", "Marketing"));
        storage.add(new Winner(2020, "John Doe", "IT"));
        storage.add(new Winner(2023, "Peter Jones", "Finance"));
    }

    //test correct add elem
    @Test
    public void testAddAndListWinners() {
        storage.add(new Winner(2020, "TestName", "IT"));

        List<String> winners = storage.listWinners();
        assertTrue(winners.contains("TestName"), "Winner should be added.");
    }

    //test invalid year
    @Test
    void testAddInvalidYear() {
        Winner testWinner = new Winner(-2020, "TestName", "IT");
        storage.add(testWinner);
        assertTrue(testWinner.getYear() < 0, "Winner should be >= 0.");
    }

    //test added invalid name
    @Test
    void testAddInvalidName() {
        Winner testWinner = new Winner(2020, null, "IT");
        storage.add(testWinner);
        assertTrue(testWinner.getName() == null, "Name should be not null.");
    }

    //test added invalid year and name
    @Test
    void testAddInvalidNameYear() {
        Winner testWinner = new Winner(-2020, null, "IT");
        storage.add(testWinner);
        assertTrue(testWinner.getName() == null || testWinner.getYear() < 0, "Invalis datas");
    }

    //test method testFindMostFrequentDepartment
    @Test
    public void testFindMostFrequentDepartment() {
        List<String> mostFrequentDepartments = storage.findMostFrequentDepartment();
        assertEquals(1, mostFrequentDepartments.size());
    }

    //test empty method testFindMostFrequentDepartment
    @Test
    void testFindMostFrequentDepartmentEmpty() {
        storage = new StorageWinners<>();
        List<String> departments = storage.findMostFrequentDepartment();
        assertEquals(0, departments.size(), "Count of one-time winners should be 0 for empty storage.");
    }

    //test method countOneTimeWinners
    @Test
    public void testCountOneTimeWinners() {
        int count = storage.countOneTimeWinners();
        assertTrue(count == 2, "Count of one-time winners should be non-negative.");
    }

    //test empty method countOneTimeWinners
    @Test
    void testCountOneTimeWinnersEmpty() {
        storage = new StorageWinners<>();
        int count = storage.countOneTimeWinners();
        assertEquals(0, count, "Count of one-time winners should be 0 for empty storage.");
    }

    //test method testListWinners
    @Test
    public void testListWinners() {
        List<String> listWinners = storage.listWinners();
        assertEquals(3, listWinners.size());
    }

    //test empty method testListWinners
    @Test
    void testListWinnersEmty() {
        storage = new StorageWinners<>();
        List<String> winners = storage.listWinners();
        assertEquals(0, winners.size(), "Count of one-time winners should be 0 for empty storage.");
    }

    //test ptrints
    @Test
    public void testPrintFindMostFrequentDepartment() {
        assertDoesNotThrow(() -> storage.printFindMostFrequentDepartment(), "Should not throw an exception while printing.");
    }

    @Test
    public void testPrintWinners() {
        assertDoesNotThrow(() -> storage.printWinners(), "Should not throw an exception while printing.");
    }

    @Test
    public void testPrintCountOneTimeWinners() {
        assertDoesNotThrow(() -> storage.printCountOneTimeWinners(), "Should not throw an exception while printing.");
    }
}
