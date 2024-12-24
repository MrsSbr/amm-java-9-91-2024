package ru.vsu.amm.java.classes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.enums.Hero;
import ru.vsu.amm.java.service.MortalCombatService;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.Map;
import java.util.stream.Collectors;

public class EmptyMortalCombatServiceTest extends MortalCombatServiceTest {

    public EmptyMortalCombatServiceTest() {
        fights = new ArrayList<>();
    }

    @Override
    @Test
    public void testFindMonthsWithMostFatalitiesInLast3Years() {
        Set<Month> expectedCollection = Arrays.stream(Month.values()).collect(Collectors.toSet());
        Assertions.assertEquals(expectedCollection,
                MortalCombatService.findMonthsWithMostFatalitiesInLast3Years(fights));
    }

    @Override
    @Test
    public void testCountVictoriesOfEveryHero() {
        Map<Hero, Integer> expectedCollection = Arrays.stream(Hero.values())
                        .collect(Collectors.toMap(i -> i, i -> 0));
        Assertions.assertEquals(expectedCollection,
                MortalCombatService.countVictoriesOfEveryHero(fights));
    }

    @Override
    @Test
    public void testFindParticipantsForEveryTournament() {
        Assertions.assertEquals(0,
                MortalCombatService.findParticipantsForEveryTournament(fights).size());
    }
}
