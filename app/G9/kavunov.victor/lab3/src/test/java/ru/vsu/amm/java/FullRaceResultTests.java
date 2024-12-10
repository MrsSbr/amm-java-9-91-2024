package ru.vsu.amm.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FullRaceResultTests {
    public static List<RaceResult> generateFullRaceResults() {
        List<RaceResult> results = new ArrayList<>();
        results.add(new RaceResult(1, LocalDate.now(), 2));
        results.add(new RaceResult(1, LocalDate.now().minusYears(2), 1));
        results.add(new RaceResult(2, LocalDate.now().minusYears(6), 8));
        results.add(new RaceResult(2, LocalDate.now(), 1));
        results.add(new RaceResult(2, LocalDate.now().minusYears(6), 12));
        results.add(new RaceResult(3, LocalDate.now(), 4));
        results.add(new RaceResult(3, LocalDate.now().minusYears(6), 21));
        return results;
    }

    public static final List<RaceResult> fullRaceResults = generateFullRaceResults();

    @Test
    public void testLastThreeYearsPrizewinnersFill() {
        Set<Integer> result = RaceResultService.getLastThreeYearsPrizewinners(fullRaceResults);
        HashSet<Integer> expected = new HashSet<>();
        expected.add(1);
        expected.add(2);
//        Assertions.assertEquals(expected, result);
        Assertions.assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }

    @Test
    public void testWinnersCountFull() {
        long result = RaceResultService.getWinnersCount(fullRaceResults);
        Assertions.assertEquals(2, result);
    }

    @Test
    public void testNewPrizewinnersLastParticipantFull() {
        Set<Integer> result = RaceResultService.getNewPrizewinnersLastParticipant(fullRaceResults);
        HashSet<Integer> expected = new HashSet<>();
        expected.add(2);
//        Assertions.assertEquals(expected, result);
        Assertions.assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }
}
