package ru.vsu.amm.java.classes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.Fight;
import ru.vsu.amm.java.enums.Hero;
import ru.vsu.amm.java.service.MortalCombatService;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class WithoutFatalitiesMortalCombatServiceTest extends MortalCombatServiceTest {

    public WithoutFatalitiesMortalCombatServiceTest() {
        super();
        fights = List.of(
                new Fight(1,
                        LocalDate.of(2022, 3, 27),
                        Hero.CETRION,
                        Hero.ERMAC,
                        Hero.ERMAC,
                        null),
                new Fight(1,
                        LocalDate.of(2020, 3, 20),
                        Hero.CETRION,
                        Hero.SCORPION,
                        Hero.SCORPION,
                        null),
                new Fight(2,
                        LocalDate.of(2023, 1, 9),
                        Hero.SUB_ZERO,
                        Hero.SCORPION,
                        Hero.SCORPION,
                        null)
        );
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
        Map<Hero, Integer> expectedCollection = Map.of(
                Hero.SCORPION, 2,
                Hero.ERMAC, 1,
                Hero.CETRION, 0,
                Hero.SUB_ZERO, 0
        );
        Assertions.assertEquals(expectedCollection,
                MortalCombatService.countVictoriesOfEveryHero(fights));
    }

    @Override
    @Test
    public void testFindParticipantsForEveryTournament() {
        Map<Integer, Set<Hero>> expectedCollection = Map.of(
                1, Set.of(
                        Hero.ERMAC,
                        Hero.SCORPION,
                        Hero.CETRION
                ),
                2, Set.of(
                        Hero.SUB_ZERO,
                        Hero.SCORPION
                )
        );
        Assertions.assertEquals(expectedCollection,
                MortalCombatService.findParticipantsForEveryTournament(fights));
    }
}
