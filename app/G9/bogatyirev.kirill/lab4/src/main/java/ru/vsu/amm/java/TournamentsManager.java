package ru.vsu.amm.java;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TournamentsManager {
    private static final Logger logger = Logger.getLogger(TournamentsManager.class.getName());


    public TournamentsManager() {
    }

    public Set<String> allFightersInTournament(int tournamentNumber, Tournaments tournaments) {
        logger.log(Level.FINE, "Starting allFightersInTournament method");

        return tournaments.getFights().stream()
                .filter(fight -> fight.getTournamentNumber() == tournamentNumber)
                .flatMap(fight -> Stream.of(fight.getFirstFighterName(), fight.getSecondFighterName()))
                .collect(Collectors.toSet());
    }

    public Map<String, Integer> countOfWins(Tournaments tournaments) {
        logger.log(Level.FINE, "Starting countOfWins method");

        return tournaments.getFights().stream()
                .collect(Collectors.groupingBy(
                        Fight::getWinner,
                        Collectors.collectingAndThen(
                                Collectors.counting(),
                                Long::intValue
                        )
                ));
    }

    public Map<String, Integer> monthWithMostFatality(Tournaments tournaments) {
        logger.log(Level.FINE, "Starting monthWithMostFatality method");
        Map<String, Integer> result = mostBloodestMonthes(tournaments);

        int max = Collections.max(result.values());

        return result.entrySet().stream()
                .filter(month -> month.getValue() == max)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    private Map<String, Integer> mostBloodestMonthes(Tournaments tournaments) {
        logger.log(Level.FINE, "Starting mostBloodestMonthes method");
        LocalDate threeYearsAgo = LocalDate.now().minusYears(3);

        return tournaments.getFights().stream()
                .filter(fight -> fight.getFightDate().isAfter(threeYearsAgo) && fight.isFatality())
                .collect(Collectors.groupingBy(
                        fight -> fight.getFightDate().getMonth().toString(),
                        Collectors.collectingAndThen(
                                Collectors.counting(),
                                Long::intValue
                )));
    }

}
