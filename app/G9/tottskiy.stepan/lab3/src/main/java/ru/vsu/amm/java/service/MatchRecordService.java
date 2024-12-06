package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.MatchRecord;

import java.util.*;
import java.util.stream.Collectors;

public class MatchRecordService {


    public Set<String> getMostFrequentBestPlayers(List<MatchRecord> records) {
        List<String> allPlayers = records.stream()
                .flatMap(record -> record.getPlayers().stream())
                .toList();

        int maxFrequency = allPlayers.stream()
                .mapToInt(player -> Collections.frequency(allPlayers, player))
                .max()
                .orElse(0);

        return allPlayers.stream()
                .filter(player -> Collections.frequency(allPlayers, player) == maxFrequency)
                .collect(Collectors.toSet());
    }

    public Set<String> getBestPlayersInAwayGames(List<MatchRecord> records) {
        return records.stream()
                .map(MatchRecord::getAwayBestPlayer)
                .collect(Collectors.toSet());
    }

    public long getSingleAwardedPlayersCount(List<MatchRecord> records) {
        List<String> allPlayers = records.stream()
                .flatMap(record -> record.getPlayers().stream())
                .toList();

        return allPlayers.stream()
                .filter(player -> Collections.frequency(allPlayers, player) == 1)
                .distinct()
                .count();
    }
}
