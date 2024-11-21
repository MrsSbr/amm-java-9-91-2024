package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.Fight;
import ru.vsu.amm.java.enums.Fatality;
import ru.vsu.amm.java.enums.Hero;
import ru.vsu.amm.java.service.MortalCombatService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MortalCombatDemo {

    public static void main(String[] args) {

        List<Fight> fights = new ArrayList<>(List.of(
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
                        Hero.CETRION,
                        Fatality.GOOD_AND_EVIL),
                new Fight(2,
                        LocalDate.of(2023, 1, 9),
                        Hero.SUB_ZERO,
                        Hero.SCORPION,
                        Hero.SCORPION,
                        null)
        ));

        System.out.println("Месяцы, в которые за последние 3 года было сделано наибольшее количество фаталити:\n");
        System.out.println(MortalCombatService.findMonthsWithMostFatalitiesInLast3Years(fights));
        System.out.print("\n");

        System.out.println("Количество побед в битвах каждого из участников:\n");
        System.out.println(MortalCombatService.countVictoriesOfEveryHero(fights));
        System.out.print("\n");

        System.out.println("Списки участников для каждого турнира:\n");
        System.out.println(MortalCombatService.findParticipantsForEveryTournament(fights));
        System.out.print("\n");
    }
}