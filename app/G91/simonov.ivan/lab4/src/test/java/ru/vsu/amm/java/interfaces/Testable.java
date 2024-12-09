package ru.vsu.amm.java.interfaces;

import org.junit.jupiter.api.Test;

public interface Testable {
    @Test
    void testFindMonthsWithMostFatalitiesInLast3Years();

    @Test
    void testCountVictoriesOfEveryHero();

    @Test
    void testFindParticipantsForEveryTournament();
}
