package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.OlympiadRecord;

import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OlympiadStatsService {

    public static List<String> findStudentsWinningEveryYear(List<OlympiadRecord> records){
        if (records == null || records.isEmpty()) {
            throw new IllegalArgumentException("Records list is null or empty.");
        }

        Map<String, Long> winnersCount = records.stream()
                .flatMap(record -> record.winners().stream())
                .collect(Collectors.groupingBy(student -> student, Collectors.counting()));

        List<String> result = winnersCount.entrySet().stream()
                .filter(entry -> entry.getValue() == records.stream().map(OlympiadRecord::year).distinct().count())
                .map(Map.Entry::getKey)
                .toList();
        return result;
    }

    public static Map<String, List<String>> findWinnersBySubjectLast10Years(List<OlympiadRecord> records, Year currentYear) {
        if (records == null || records.isEmpty()) {
            throw new IllegalArgumentException("Records list is null or empty.");
        }

        return records.stream()
                .filter(record -> record.year().isAfter(currentYear.minusYears(10)))
                .collect(Collectors.groupingBy(
                        record -> record.subject().name(),
                        Collectors.mapping(
                                record -> record.winners(),
                                Collectors.flatMapping(List::stream, Collectors.toSet())
                        )
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> List.copyOf(entry.getValue())
                ));
    }

    public static String findStudentWithMostSubjects(List<OlympiadRecord> records) {
        if (records == null || records.isEmpty()) {
            throw new IllegalArgumentException("Records list is null or empty.");
        }

        Map<String, Long> studentSubjects = records.stream()
                .flatMap(record -> record.winners().stream()
                        .map(winner -> Map.entry(winner, record.subject())))
                .distinct()
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.counting()));

        String topStudent = studentSubjects.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
        return topStudent;
    }
}
