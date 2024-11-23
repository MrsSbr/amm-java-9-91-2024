package ru.vsu.amm.java.classes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.service.MortalCombatService;

import java.util.Map;
import java.util.Set;

public class NullMortalCombatServiceTest extends MortalCombatServiceTest {

    @Override
    @Test
    public void testFindMonthsWithMostFatalitiesInLast3Years() {
        Assertions.assertEquals(Set.of(), MortalCombatService.findMonthsWithMostFatalitiesInLast3Years(fights));
    }

    @Override
    @Test
    public void testCountVictoriesOfEveryHero() {
        Assertions.assertEquals(Map.of(), MortalCombatService.countVictoriesOfEveryHero(fights));
    }

    @Override
    @Test
    public void testFindParticipantsForEveryTournament() {
        Assertions.assertEquals(Map.of(), MortalCombatService.findParticipantsForEveryTournament(fights));
    }
}
