package ru.vsu.amm.java.classes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.service.MortalCombatService;

public class NullMortalCombatServiceTest extends MortalCombatServiceTest {

    @Override
    @Test
    public void testFindMonthsWithMostFatalitiesInLast3Years() {
        Assertions.assertEquals(0,
                MortalCombatService.findMonthsWithMostFatalitiesInLast3Years(fights).size());
    }

    @Override
    @Test
    public void testCountVictoriesOfEveryHero() {
        Assertions.assertEquals(0,
                MortalCombatService.countVictoriesOfEveryHero(fights).size());
    }

    @Override
    @Test
    public void testFindParticipantsForEveryTournament() {
        Assertions.assertEquals(0,
                MortalCombatService.findParticipantsForEveryTournament(fights).size());
    }
}
