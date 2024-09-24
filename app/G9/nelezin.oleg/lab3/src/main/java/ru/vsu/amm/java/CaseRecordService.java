package ru.vsu.amm.java;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CaseRecordService {

    public Set<String> getNotConvictedPeople(List<CaseRecord> records) {
        return records.stream()
                .filter(c -> !c.isConvicted())
                .map(CaseRecord::getRespondent)
                .collect(Collectors.toSet());
    }

    public Set<String> getPeopleMultipleArticles(List<CaseRecord> records) {
        LocalDate tenYearsAgo = LocalDate.now().minusYears(10);
        return records.stream()
                .filter(c -> c.getDate().isAfter(tenYearsAgo))
                .collect(Collectors.groupingBy(CaseRecord::getRespondent,
                        Collectors.mapping(CaseRecord::getArticle, Collectors.toSet())))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .map(entry -> entry.getKey())
                .collect(Collectors.toSet());
    }

    public Set<String> getFrequentPlaintiffs(List<CaseRecord> records) {
        LocalDate threeYearsAgo = LocalDate.now().minusYears(3);
        return records.stream()
                .filter(c -> c.getDate().isAfter(threeYearsAgo))
                .collect(Collectors.groupingBy(CaseRecord::getPlaintiff, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(entry -> entry.getKey())
                .collect(Collectors.toSet());
    }
}
