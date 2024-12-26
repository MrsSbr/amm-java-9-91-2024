package ru.vsu.amm.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class NullRaceResultTests {
    public static final List<RaceResult> nullRaceResults = null;

    @Test
    public void testLastThreeYearsPrizewinnersNull() {
        Assertions.assertThrows(NullPointerException.class,
                () -> RaceResultService.getLastThreeYearsPrizewinners(nullRaceResults));
    }

    @Test
    public void testWinnersCountNull() {
        Assertions.assertThrows(NullPointerException.class,
                () -> RaceResultService.getWinnersCount(nullRaceResults));
    }

    @Test
    public void testNewPrizewinnersLastParticipantNull() {
        Assertions.assertThrows(NullPointerException.class,
                () -> RaceResultService.getNewPrizewinnersLastParticipant(nullRaceResults));
    }
}
