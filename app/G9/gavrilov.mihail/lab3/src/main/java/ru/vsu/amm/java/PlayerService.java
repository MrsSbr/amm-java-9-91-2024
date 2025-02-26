package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayerService {

    public static List<String> findMostFrequentPlayers(List<Match> matches) {
        List<String> allBestPlayers = matches.stream()
                .flatMap(match -> Stream.of(match.getHomeBestPlayer(), match.getAwayBestPlayer()))
                .toList();

        int maxFrequency = allBestPlayers.stream()
                .mapToInt(player -> Collections.frequency(allBestPlayers, player))
                .max()
                .orElse(0);
        if (maxFrequency != 0) {
            return allBestPlayers.stream()
                    .filter(player -> Collections.frequency(allBestPlayers, player) == maxFrequency)
                    .distinct()
                    .toList();
        } else {
            return new ArrayList<>();
        }
    }

    public static Set<String> findAwayBestPlayers(List<Match> matches) {
        return matches.stream()
                .map(Match::getAwayBestPlayer)
                .collect(Collectors.toSet());
    }

    public static long findPlayersBestOnce(List<Match> matches) {
        List<String> allBestPlayers = matches.stream()
                .flatMap(match -> Stream.of(match.getHomeBestPlayer(), match.getAwayBestPlayer()))
                .toList();

        return allBestPlayers.stream()
                .filter(player -> Collections.frequency(allBestPlayers, player) == 1)
                .count();
    }
}