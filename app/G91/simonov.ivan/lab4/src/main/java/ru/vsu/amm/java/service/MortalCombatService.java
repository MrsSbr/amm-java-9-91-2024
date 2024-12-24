package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.Fight;
import ru.vsu.amm.java.enums.Hero;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MortalCombatService {

    private static final Logger logger = Logger.getLogger(MortalCombatService.class.getName());
    private static final String PATTERN_PATH = "app/G91/simonov.ivan/lab4/src/main/java/ru"
            + "/vsu/amm/java/logs/mortal-combat-service-logs.log";

    static {
        try {
            FileHandler fileHandler = new FileHandler(PATTERN_PATH);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "Creation of log file for MortalCombatService failed with an error: ", e);
        }
    }

    public static Set<Month> findMonthsWithMostFatalitiesInLast3Years(List<Fight> fights) {

        logger.log(Level.INFO, "Detected try to find months with most fatalities in last 3 years");

        try {
            LocalDate minDate = LocalDate.now().minusYears(3);
            Map<Month, Long> fatalitiesPerMonth = fights.stream()
                    .filter(fight -> fight.date().isAfter(minDate)
                            && fight.fatality() != null)
                    .collect(Collectors.groupingBy(fight -> fight.date().getMonth(), Collectors.counting()));

            Long maxFatalities = fatalitiesPerMonth.values()
                    .stream()
                    .max(Long::compareTo)
                    .orElse(0L);

            Set<Month> months = Arrays.stream(Month.values())
                    .filter(month -> {
                        Long monthFatalities = fatalitiesPerMonth.get(month);
                        return monthFatalities == null && maxFatalities == 0
                                || monthFatalities != null && monthFatalities.equals(maxFatalities);
                    })
                    .collect(Collectors.toSet());

            logger.log(Level.INFO, "Try to find months "
                    + "with most fatalities in last 3 years completed successfully");

            return months;
        }
        catch (NullPointerException e) {
            logger.log(Level.SEVERE, "Try to find months with "
                    + "most fatalities in last 3 years failed with an error: ", e);
            return new HashSet<>();
        }
    }

    public static Map<Hero, Integer> countVictoriesOfEveryHero(List<Fight> fights) {

        logger.log(Level.SEVERE, "Detected try to count victories of every hero");

        try {
            Map<Hero, Integer> victories = Arrays.stream(Hero.values())
                    .collect(Collectors.toMap(hero -> hero, hero -> 0));

            fights.forEach(fight -> victories.put(fight.winner(), victories.get(fight.winner()) + 1));

            logger.log(Level.SEVERE, "Try to count victories of every hero completed successfully");

            return victories;
        }
        catch (NullPointerException e) {
            logger.log(Level.SEVERE, "Try to count victories of every hero failed with an error: ", e);
            return new HashMap<>();
        }
    }

    public static Map<Integer, Set<Hero>> findParticipantsForEveryTournament(List<Fight> fights) {

        logger.log(Level.INFO, "Detected try to find participants for every tournament");

        try {
            Map<Integer, Set<Hero>> participants = fights.stream()
                    .flatMap(fight -> Stream.of(
                            new AbstractMap.SimpleEntry<>(fight.tournamentNum(), fight.participant1()),
                            new AbstractMap.SimpleEntry<>(fight.tournamentNum(), fight.participant2())
                    ))
                    .collect(Collectors.groupingBy(Map.Entry::getKey,
                            Collectors.mapping(Map.Entry::getValue, Collectors.toSet())));

            logger.log(Level.INFO, "Try to find participants for every tournament completed successfully");

            return participants;
        }
        catch (NullPointerException e) {
            logger.log(Level.SEVERE, "Try to find participants for every tournament failed with an error:", e);
            return new HashMap<>();
        }
    }
}
