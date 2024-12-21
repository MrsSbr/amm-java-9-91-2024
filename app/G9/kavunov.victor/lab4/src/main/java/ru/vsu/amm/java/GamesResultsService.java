package ru.vsu.amm.java;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GamesResultsService {
    public static Set<Team> getTopThree(List<GameResult> gamesResults) {
        Map<Team, Integer> allTeamsPoints = gamesResults.stream()
                .flatMap(gameResult -> Stream.of(
                        new AbstractMap.SimpleEntry<>(gameResult.homeTeam(), gameResult.getHomeTeamPoints()),
                        new AbstractMap.SimpleEntry<>(gameResult.awayTeam(), gameResult.getAwayTeamPoints())
                ))
                .collect(Collectors.groupingBy((Map.Entry::getKey),
                        Collectors.summingInt(Map.Entry::getValue)));

        Map<Team, Integer> topThreeTeams = allTeamsPoints.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));

        return topThreeTeams.keySet();
    }

    public static Set<Team> getHomeTeamsWithoutAwayGoals(List<GameResult> gamesResults) {
        return gamesResults.stream()
                .filter(gameResult -> gameResult.awayTeamGoals() == 0)
                .map(GameResult::homeTeam)
                .collect(Collectors.toSet());
    }

    public static Map<Team, Set<Team>> getTeamsWithDefeatedTeams(List<GameResult> gamesResults) {
        return gamesResults.stream()
                .filter(gameResult -> gameResult.awayTeamGoals() != gameResult.homeTeamGoals())
                .flatMap(gameResult -> {
                    if (gameResult.homeTeamGoals() > gameResult.awayTeamGoals()) {
                        return Stream.of(new AbstractMap.SimpleEntry<>(gameResult.homeTeam(), gameResult.awayTeam()));
                    }
                    return Stream.of(new AbstractMap.SimpleEntry<>(gameResult.awayTeam(), gameResult.homeTeam()));
                })
                .collect(Collectors.groupingBy((Map.Entry::getKey),
                        Collectors.mapping(Map.Entry::getValue, Collectors.toSet())));
    }
}
