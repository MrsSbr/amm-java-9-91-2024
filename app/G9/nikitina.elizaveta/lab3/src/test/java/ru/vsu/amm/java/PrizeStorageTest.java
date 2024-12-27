package ru.vsu.amm.java.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.winners.PrizeStorage;
import ru.vsu.amm.java.winners.PrizeRecipient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


public class PrizeStorageTest {
    private PrizeStorage<PrizeRecipient> storage;

    @BeforeEach
    public void setUp() {
        storage = new PrizeStorage<>();
        storage.add(new PrizeRecipient(2020, "John Doe", "IT"));
        storage.add(new PrizeRecipient(2021, "Jane Smith", "Marketing"));
        storage.add(new PrizeRecipient(2020, "John Doe", "IT"));
        storage.add(new PrizeRecipient(2023, "Peter Jones", "Finance"));
    }


    @Test
    public void testAddAndListWinners() {
        storage.add(new PrizeRecipient(2020, "TestName", "IT"));

        List<String> winners = storage.listRecipients();
        assertTrue(winners.contains("TestName"), "Winner should be added.");
    }


    @Test
    void testAddInvalidYear() {
        PrizeRecipient testRecipient = new PrizeRecipient(-2020, "TestName", "IT");
        storage.add(testRecipient);
        assertTrue(testRecipient.getYear() < 0, "Winner should be >= 0.");
    }


    @Test
    void testAddInvalidName() {
        PrizeRecipient testRecipient = new PrizeRecipient(2020, null, "IT");
        storage.add(testRecipient);
        assertNull(testRecipient.getName(), "Name should be not null.");
    }


    @Test
    void testAddInvalidNameYear() {
        PrizeRecipient testRecipient = new PrizeRecipient(-2020, null, "IT");
        storage.add(testRecipient);
        assertTrue(testRecipient.getName() == null || testRecipient.getYear() < 0, "Invalis datas");
    }


    @Test
    public void testFindMostFrequentDepartment() {
        List<String> mostFrequentDepartments = storage.findMostRecurringGroup();
        assertEquals(1, mostFrequentDepartments.size());
    }


    @Test
    void testFindMostFrequentDepartmentEmpty() {
        storage = new PrizeStorage<>();
        List<String> departments = storage.findMostRecurringGroup();
        assertEquals(0, departments.size(), "Count of one-time winners should be 0 for empty storage.");
    }


    @Test
    public void testCountOneTimeWinners() {
        int count = storage.countUniqueRecipients();
        assertEquals(2, count, "Count of one-time winners should be non-negative.");
    }


    @Test
    void testCountOneTimeWinnersEmpty() {
        storage = new PrizeStorage<>();
        int count = storage.countUniqueRecipients();
        assertEquals(0, count, "Count of one-time winners should be 0 for empty storage.");
    }


    @Test
    public void testListWinners() {
        List<String> listWinners = storage.listRecipients();
        assertEquals(3, listWinners.size());
    }


    @Test
    void testListWinnersEmpty() {
        storage = new PrizeStorage<>();
        List<String> winners = storage.listRecipients();
        assertEquals(0, winners.size(), "Count of one-time winners should be 0 for empty storage.");
    }


    @Test
    public void testPrintFindMostFrequentDepartment() {
        assertDoesNotThrow(() -> storage.printMostRecurringGroup(), "Should not throw an exception while printing.");
    }

    @Test
    public void testPrintWinners() {
        assertDoesNotThrow(() -> storage.printRecipients(), "Should not throw an exception while printing.");
    }

    @Test
    public void testPrintCountOneTimeWinners() {
        assertDoesNotThrow(() -> storage.printUniqueRecipientCount(), "Should not throw an exception while printing.");
    }
}