package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.Fight;
import ru.vsu.amm.java.enums.Hero;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MortalCombatService {

    public static Map<Hero, Integer> countVictoriesOfEveryHero(List<Fight> fights) {

        Map<Hero, Integer> victories = new HashMap<>();

        Arrays.stream(Hero.values())
                .forEach(i -> victories.put(i, 0));

        fights.forEach(i -> victories.put(i.winner(), victories.get(i.winner()) + 1));

        return victories;
    }

    public static Map<Integer, Set<Hero>> findParticipantsForEveryTournament(List<Fight> fights) {

        return fights.stream()
                .flatMap(i -> Stream.of(
                        new AbstractMap.SimpleEntry<>(i.tournamentNum(), i.participant1()),
                        new AbstractMap.SimpleEntry<>(i.tournamentNum(), i.participant2())
                ))
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toSet())));

/*        fights.forEach(i -> {

                    Set<Hero> curParticipants = participantOfTournaments.get(i.tournamentNum());

                    if (curParticipants == null) {
                        curParticipants = new HashSet<>();
                    }

                    curParticipants.addAll(List.of(i.participant1(), i.participant2()));
                    participantOfTournaments.put(i.tournamentNum(), curParticipants);
                });*/
    }
}
