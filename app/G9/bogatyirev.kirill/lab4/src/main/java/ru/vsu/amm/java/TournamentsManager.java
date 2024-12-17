package ru.vsu.amm.java;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TournamentsManager {
    private static final Logger logger = Logger.getLogger(TournamentsManager.class.getName());
    private List<Fight> fights;

    public TournamentsManager(Tournaments tournaments) {
        fights = tournaments.getFights();
    }

    public TournamentsManager() {
    }

    public Set<String> allFightersInTournament(int tournamentNumber) {
        logger.log(Level.FINE, "Starting allFightersInTournament method");

        return fights.stream()
                .filter(fight -> fight.getTournamentNumber() == tournamentNumber)
                .flatMap(fight -> Stream.of(fight.getFirstFighterName(), fight.getSecondFighterName()))
                .collect(Collectors.toSet());
    }

    public Map<String, Integer> countOfWins() {
        logger.log(Level.FINE, "Starting countOfWins method");

        Map<String, Integer> result = new HashMap<>();

        for(Fight fight : fights) {
            String winner = fight.getWinner();

            result.put(winner, result.getOrDefault(winner, 0) + 1);
            logger.fine("Count wins for fighter " + winner + " " + result.get(winner));
        }

        return result;
    }

    public Map<String, Integer> monthWithMostFatality() {
        logger.log(Level.FINE, "Starting monthWithMostFatality method");
        Map<String, Integer> result = mostBloodestMonthes();

        int max = Collections.max(result.values());

        return result.entrySet().stream()
                .filter(month -> month.getValue() == max)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    private Map<String, Integer> mostBloodestMonthes() {
        logger.log(Level.FINE, "Starting mostBloodestMonthes method");
        LocalDate threeYearsAgo = LocalDate.now().minusYears(3);
        Map<String, Integer> monthes = new HashMap<>();

        for(Fight fight: fights) {
            LocalDate fightDate = fight.getFightDate();

            if(fightDate.isAfter(threeYearsAgo) && fight.isFatality()) {
                String month = fightDate.getMonth().toString();
                monthes.put(month, monthes.getOrDefault(month, 0) + 1);
                logger.fine(month + " with fatality was putting in map");
            }
        }
        return monthes;
    }

    public List<Fight> getFights() {
        return fights;
    }
}
