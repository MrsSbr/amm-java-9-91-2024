package ru.vsu.amm.java.classes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.service.MortalCombatService;

public class NullMortalCombatServiceTest extends MortalCombatServiceTest {

    @Override
    @Test
    public void testFindMonthsWithMostFatalitiesInLast3Years() {
        Assertions.assertThrows(NullPointerException.class,
                () -> MortalCombatService.findMonthsWithMostFatalitiesInLast3Years(fights));
    }

    @Override
    @Test
    public void testCountVictoriesOfEveryHero() {
        Assertions.assertThrows(NullPointerException.class,
                () -> MortalCombatService.countVictoriesOfEveryHero(fights));
    }

    @Override
    @Test
    public void testFindParticipantsForEveryTournament() {
        Assertions.assertThrows(NullPointerException.class,
                () -> MortalCombatService.findParticipantsForEveryTournament(fights));
    }
}
