package ru.vsu.amm.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Model.PrizeRecipient;
import ru.vsu.amm.java.Service.PrizeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PrizeServiceTest {
    private final PrizeService service = new PrizeService();
    private List<PrizeRecipient> data;

    @BeforeEach
    public void setUp() {
        data = new ArrayList<>();
        data.add(new PrizeRecipient(2020, "John Doe", "IT"));
        data.add(new PrizeRecipient(2021, "Jane Smith", "Marketing"));
        data.add(new PrizeRecipient(2020, "John Doe", "IT"));
        data.add(new PrizeRecipient(2023, "Peter Jones", "Finance"));
    }


    @Test
    public void testAddAndListWinners() {
        data.add(new PrizeRecipient(2020, "TestName", "IT"));
        List<String> expected = List.of("John Doe", "Jane Smith", "Peter Jones", "TestName");
        List<String> winners = service.countUniqueRecipients(data);
        assertTrue(winners.containsAll(expected) && expected.containsAll(winners));
    }


    @Test
    public void testFindMostFrequentDepartment() {
        List<String> mostFrequentDepartments = service.findMostRecurringGroup(data);
        List<String> expected = List.of("IT");
        assertTrue(mostFrequentDepartments.containsAll(expected) && expected.containsAll(mostFrequentDepartments));
    }


    @Test
    void testFindMostFrequentDepartmentEmpty() {
        data = new ArrayList<>();
        List<String> mostFrequentDepartments = service.findMostRecurringGroup(data);
        List<String> expected = new ArrayList<>();
        assertTrue(mostFrequentDepartments.containsAll(expected) && expected.containsAll(mostFrequentDepartments));
    }


    @Test
    public void testCountOneTimeWinners() {
        Long count = service.countRecipientsWithOneWin(data).get();
        Long expected = 2L;
        assertEquals(expected, count, "Count of one-time winners should be non-negative.");
    }


    @Test
    void testCountOneTimeWinnersEmpty() {
        data = new ArrayList<>();
        Long count = service.countRecipientsWithOneWin(data).get();
        Long expected = 0L;
        assertEquals(expected, count, "Count of one-time winners should be 0 for empty storage.");
    }

    @Test
    void testCountOneTimeWinnersNull() {
        data = null;
        Optional<Long> count = service.countRecipientsWithOneWin(data);
        assertEquals(Optional.empty(), count, "Count of one-time winners should be 0 for empty storage.");
    }


}