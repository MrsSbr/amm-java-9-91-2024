package ru.vsu.amm.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EmptyRaceResultTests {
    public static final List<RaceResult> emptyRaceResults = new ArrayList<>();

    @Test
    public void testLastThreeYearsPrizewinnersEmpty() {
        Set<Integer> result = RaceResultService.getLastThreeYearsPrizewinners(emptyRaceResults);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testWinnersCountEmpty() {
        long result = RaceResultService.getWinnersCount(emptyRaceResults);
        Assertions.assertEquals(0, result);
    }

    @Test
    public void testNewPrizewinnersLastParticipantEmpty() {
        Set<Integer> result = RaceResultService.getNewPrizewinnersLastParticipant(emptyRaceResults);
        Assertions.assertTrue(result.isEmpty());
    }
}
