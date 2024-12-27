package ru.vsu.amm.java.main.Service;

import ru.vsu.amm.java.main.Enum.KindOfSport;
import ru.vsu.amm.java.main.OlympicRecord.OlympicMedalsRecord;

import java.util.*;
import java.util.stream.Collectors;

public class OlympicStatsService {

    public static List<String> TopMedalCountryResult(List<OlympicMedalsRecord> data) {
        if (data == null || data.isEmpty()) {
            return List.of();
        } else {
            Map<String, Long> countryMedalCounts = data.stream()
                    .collect(Collectors.groupingBy(
                            OlympicMedalsRecord::country,
                            Collectors.counting()
                    ));

            return countryMedalCounts.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder()))
                    .collect(Collectors.groupingBy(Map.Entry::getValue,
                            Collectors.mapping(Map.Entry::getKey, Collectors.toList())))
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.<Long, List<String>>comparingByKey(Comparator.reverseOrder()))
                    .limit(3)
                    .flatMap(entry -> entry.getValue().stream())
                    .toList();
        }
    }


    public static Map<KindOfSport, Set<String>>
    AthletesWithMedalsAndSport(List<OlympicMedalsRecord> data) {
        if (data == null || data.isEmpty()) {
            return Map.of();
        } else {
            return data.stream()
                    .collect(Collectors.groupingBy(
                            OlympicMedalsRecord::sport,
                            Collectors.mapping(OlympicMedalsRecord::athlete, Collectors.toSet())
                    ));
        }
    }

    public static Set<String> AthleteWithMaxMedals(List<OlympicMedalsRecord> data) {
        if (data == null || data.isEmpty()) {
            return new HashSet<>();
        } else {
            List<String> allBestAthletes = data.stream()
                    .map(OlympicMedalsRecord::athlete).toList();

            int maxFrequency = allBestAthletes.stream().mapToInt((athlete) -> {
                return Collections.frequency(allBestAthletes, athlete);
            }).max().orElse(0);

            return allBestAthletes.stream().filter((athlete) -> {
                return Collections.frequency(allBestAthletes, athlete) == maxFrequency;
            }).collect(Collectors.toSet());
        }
    }
}
