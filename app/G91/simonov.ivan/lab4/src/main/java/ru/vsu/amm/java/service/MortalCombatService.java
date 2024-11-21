package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.Fight;
import ru.vsu.amm.java.enums.Hero;

import java.util.*;

public class MortalCombatService {

    public static Map<Hero, Integer> CountVictoriesOfEveryHero(List<Fight> fights) {

        Map<Hero, Integer> victories = new HashMap<>();

        Arrays.stream(Hero.values())
                .forEach(i -> victories.put(i, 0));

        fights.forEach(i -> victories.put(i.winner(), victories.get(i.winner()) + 1));

        return victories;
    }
}
