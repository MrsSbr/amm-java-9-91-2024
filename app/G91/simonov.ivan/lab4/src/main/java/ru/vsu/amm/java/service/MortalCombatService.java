package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.Fight;
import ru.vsu.amm.java.enums.Hero;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.AbstractMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MortalCombatService {

    public static Set<Month> findMonthsWithMostFatalitiesInLast3Years(List<Fight> fights) {

        Map<Month, Long> fatalitiesPerMonth = fights.stream()
                .filter(i -> i.date().isAfter(LocalDate.now().minusYears(3)) && i.fatality() != null)
                .collect(Collectors.groupingBy(i -> i.date().getMonth(), Collectors.counting()));

        Long maxFatalities = fatalitiesPerMonth.values()
                .stream()
                .max(Long::compareTo)
                .orElse(0L);

        return Arrays.stream(Month.values())
                .filter(i -> {
                    Long monthFatalities = fatalitiesPerMonth.get(i);
                    return monthFatalities == null && maxFatalities == 0
                            || monthFatalities != null && monthFatalities.equals(maxFatalities);
                })
                .collect(Collectors.toSet());
    }

    public static Map<Hero, Integer> countVictoriesOfEveryHero(List<Fight> fights) {

        Map<Hero, Integer> victories = Arrays.stream(Hero.values())
                .collect(Collectors.toMap(i -> i, i -> 0));

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
