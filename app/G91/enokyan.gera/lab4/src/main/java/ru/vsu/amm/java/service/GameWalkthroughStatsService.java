package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.GameWalkthrough;
import ru.vsu.amm.java.enums.Genre;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class GameWalkthroughStatsService {
    private GameWalkthroughStatsService() {
    }

    public static Genre findHighestRatedGenre(List<GameWalkthrough> gameWalkthroughList)
            throws NullPointerException {
        if (gameWalkthroughList == null) {
            throw new NullPointerException();
        }

        return gameWalkthroughList.stream()
                .collect(Collectors.groupingBy(
                        GameWalkthrough::genre,
                        Collectors.averagingInt(g -> g.rating().value))
                )
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(NullPointerException::new);
    }

    public static YearMonth findMonthWithMostPlaytime(List<GameWalkthrough> gameWalkthroughList)
            throws NullPointerException {
        if (gameWalkthroughList == null) {
            throw new NullPointerException();
        }

        return gameWalkthroughList.stream()
                .collect(Collectors.groupingBy(
                        gw -> YearMonth.from(gw.date()),
                        Collectors.summingInt(GameWalkthrough::time))
                )
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(NullPointerException::new);
    }

    public static List<String> findAllMultipleWalkthroughGames(List<GameWalkthrough> gameWalkthroughList)
            throws NullPointerException {
        if (gameWalkthroughList == null) {
            throw new NullPointerException();
        }

        return gameWalkthroughList.stream()
                .collect(Collectors.groupingBy(
                        GameWalkthrough::name,
                        Collectors.counting())
                )
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();
    }
}