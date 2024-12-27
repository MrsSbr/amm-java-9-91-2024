package ru.vsu.amm.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Model.PrizeRecipient;
import ru.vsu.amm.java.Service.PrizeService;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PrizeServiceTest {
    private final PrizeService service = new PrizeService();
    private Set<PrizeRecipient> data;

    @BeforeEach
    public void setUp() {
        data = new HashSet<>();
        data.add(new PrizeRecipient(2020, "John Doe", "IT"));
        data.add(new PrizeRecipient(2021, "Jane Smith", "Marketing"));
        data.add(new PrizeRecipient(2020, "John Doe", "IT"));
        data.add(new PrizeRecipient(2023, "Peter Jones", "Finance"));
    }


    @Test
    public void testAddAndListWinners() {
        data.add(new PrizeRecipient(2020, "TestName", "IT"));
        Set<String> expected = Set.of("John Doe", "Jane Smith", "Peter Jones", "TestName");
        Set<String> winners = service.countUniqueRecipients(data);
        assertTrue(winners.containsAll(expected) && expected.containsAll(winners));
    }


    @Test
    public void testFindMostFrequentDepartment() {
        Set<String> mostFrequentDepartments = service.findMostRecurringGroup(data);
        Set<String> expected = Set.of("IT");
        assertTrue(mostFrequentDepartments.containsAll(expected) && expected.containsAll(mostFrequentDepartments));
    }


    @Test
    void testFindMostFrequentDepartmentEmpty() {
        data = new HashSet<>();
        Set<String> mostFrequentDepartments = service.findMostRecurringGroup(data);
        Set<String> expected = new HashSet<>();
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
        data = new HashSet<>();
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