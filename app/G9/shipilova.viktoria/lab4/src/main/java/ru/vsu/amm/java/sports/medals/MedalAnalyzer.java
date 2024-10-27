package ru.vsu.amm.java.sports.medals;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MedalAnalyzer {
    public List<Country> topThreeInMedals(List<Medal> medals) {
        return medals.stream()
                .collect(Collectors.groupingBy(Medal::getCountry, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<Country, Long>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public Map<KindOfSport, List<String>> athletesWhoTookPlaces(List<Medal> medals) {
        return medals.stream()
                .collect(Collectors.groupingBy(
                        Medal::getKindOfSport,
                        Collectors.mapping(Medal::getAthlete, Collectors.toList())
                ));
    }

    public List<String> athleteWithTheMostMedals(List<Medal> medals) {
        int maxMedals = medals.stream()
                .collect(Collectors.groupingBy(Medal::getAthlete, Collectors.summingInt(m -> 1)))
                .values().stream()
                .max(Integer::compare)
                .orElse(0);

        return medals.stream()
                .collect(Collectors.groupingBy(Medal::getAthlete, Collectors.summingInt(m -> 1)))
                .entrySet().stream()
                .filter(entry -> entry.getValue() == maxMedals)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
