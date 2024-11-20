package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.MatchRecord;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.function.Function;

public class MatchRecordService {


    public Set<String> getMostFrequentBestPlayers(List<MatchRecord> records) {
        long maxCount = records.stream()
                .flatMap(record -> List.of(record.getHomeBestPlayer(), record.getAwayBestPlayer()).stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .values().stream()
                .max(Long::compare)
                .orElse(0L);

        return records.stream()
                .flatMap(record -> List.of(record.getHomeBestPlayer(), record.getAwayBestPlayer()).stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() == maxCount)
                .map(entry -> entry.getKey())
                .collect(Collectors.toSet());
    }


    public Set<String> getBestPlayersInAwayGames(List<MatchRecord> records) {
        return records.stream()
                .map(MatchRecord::getAwayBestPlayer)
                .collect(Collectors.toSet());
    }


    public long getSingleAwardedPlayersCount(List<MatchRecord> records) {
        return records.stream()
                .flatMap(record -> List.of(record.getHomeBestPlayer(), record.getAwayBestPlayer()).stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .values().stream()
                .filter(count -> count == 1)
                .count();
    }
}
