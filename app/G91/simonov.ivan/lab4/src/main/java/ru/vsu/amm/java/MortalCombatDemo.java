package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.Fight;
import ru.vsu.amm.java.enums.Fatality;
import ru.vsu.amm.java.enums.Hero;
import ru.vsu.amm.java.service.MortalCombatService;

import java.util.ArrayList;
import java.util.List;

public class MortalCombatDemo {
    public static void main(String[] args) {

        List<Fight> fights = new ArrayList<>(List.of(
                new Fight(1, Hero.CETRION, Hero.ERMAC, Hero.ERMAC, Fatality.CRISPY),
                new Fight(1, Hero.CETRION, Hero.SCORPION, Hero.CETRION, Fatality.GOOD_AND_EVIL),
                new Fight(2, Hero.SUB_ZERO, Hero.SCORPION, Hero.SCORPION, Fatality.SHREDDED)
        ));


        System.out.println("Количество побед в битвах каждого из участников:");
        System.out.println(MortalCombatService.countVictoriesOfEveryHero(fights));

        System.out.println("Списки участников для каждого турнира:");
        System.out.println(MortalCombatService.findParticipantsForEveryTournament(fights));
    }
}