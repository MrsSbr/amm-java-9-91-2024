package ru.vsu.amm.java.Service;

import ru.vsu.amm.java.Model.PrizeRecipient;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Optional;
import java.util.AbstractMap;
import java.util.stream.Collectors;

public class PrizeService {

    public Set<String> findMostRecurringGroup(List<PrizeRecipient> data) {
        if (data == null || data.isEmpty()) return new HashSet<>();

        Map<String, Long> teamWins = data.stream()
                .collect(Collectors.groupingBy(PrizeRecipient::teamName, Collectors.counting()));

        long maxWins = teamWins.values().stream().max(Long::compare).orElse(0L);

        return teamWins.entrySet().stream()
                .filter(entry -> entry.getValue() == maxWins)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

    }

    public Set<String> countUniqueRecipients(List<PrizeRecipient> data) {
        if (data == null || data.isEmpty()) return new HashSet<>();

        return data.stream().map(PrizeRecipient::name).collect(Collectors.toSet());
    }


    public Optional<Long> countRecipientsWithOneWin(List<PrizeRecipient> data) {
        if (data == null) return Optional.empty();

        Map<AbstractMap.SimpleEntry<String, String>, Long> frequencyMap = data.stream()
                .collect(Collectors.groupingBy(e -> new AbstractMap.SimpleEntry<>(e.name(), e.teamName()), Collectors.counting()));

        return Optional.of(data.stream()
                .filter(e -> frequencyMap.get(new AbstractMap.SimpleEntry<>(e.name(), e.teamName())) == 1)
                .count());

    }
}