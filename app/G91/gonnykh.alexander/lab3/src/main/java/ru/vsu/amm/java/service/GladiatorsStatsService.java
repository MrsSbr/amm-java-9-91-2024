package ru.vsu.amm.java.service;

import ru.vsu.amm.java.battlerecord.BattleRecord;
import ru.vsu.amm.java.enums.Animal;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GladiatorsStatsService {

    public static List<Animal> getDeadliestAnimal(List<BattleRecord> battleRecords) {

        Map<Animal, Long> animalWins = battleRecords.stream()
                .filter(elem -> !elem.gladiatorWon() && !elem.gladiatorPardoned())
                .collect(Collectors.groupingBy(BattleRecord::animal, Collectors.counting()));

        Long maxWins = animalWins.values().stream()
                .max(Long::compareTo)
                .orElse(0L);

        return animalWins.entrySet().stream()
                .filter(entry -> entry.getValue().equals(maxWins))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public static List<String> getGladiatorsNotFromLudus(List<BattleRecord> battleRecords) {
        return battleRecords.stream().filter(elem -> elem.ludus() == null || elem.ludus().isBlank())
                .sorted(Comparator.comparing(BattleRecord::date))
                .collect(Collectors.groupingBy(BattleRecord::name))
                .entrySet().stream().filter(entry ->
                {
                    long survivalCount = entry.getValue().stream()
                            .filter(battle -> battle.gladiatorWon()
                                    || battle.gladiatorPardoned()).count();

                    BattleRecord lastRecord = entry.getValue().getLast();

                    return (!lastRecord.gladiatorWon()
                            && !lastRecord.gladiatorPardoned())
                            && survivalCount >= 3;

                }).map(Map.Entry::getKey).collect(Collectors.toList());

    }

    public static List<String> getBestLudus(List<BattleRecord> battleRecords) {
        Map<String, Long> records = battleRecords.stream()
                .filter(elem -> elem.ludus() != null && !elem.ludus().isBlank())
                .filter(BattleRecord::gladiatorWon)
                .collect(Collectors.groupingBy(BattleRecord::ludus, Collectors.counting()));

        Long maxWins = records.values().stream()
                .max(Long::compareTo)
                .orElse(0L);

        return records.entrySet().stream()
                .filter(entry -> entry.getValue().equals(maxWins))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}