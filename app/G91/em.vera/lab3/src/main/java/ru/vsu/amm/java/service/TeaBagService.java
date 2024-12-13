package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.BestTeaYear;
import ru.vsu.amm.java.entity.HaviestTeaBag;
import ru.vsu.amm.java.entity.TeaBag;
import ru.vsu.amm.java.enums.TeaType;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class TeaBagService {

    public static List<BestTeaYear> getBestTeaYears(List<TeaBag> teaBags) {
        if (teaBags == null || teaBags.isEmpty()) {
            return Collections.emptyList();
        }

        return teaBags.stream()
                .collect(Collectors.groupingBy(TeaBag::name))
                .entrySet().stream()
                .map(entry -> {
                    TeaType teaType = entry.getKey();
                    return entry.getValue().stream()
                            .collect(Collectors.groupingBy(TeaBag::year,
                                    Collectors.summingInt(TeaBag::weight)
                            ))
                            .entrySet().stream()
                            .max(Map.Entry.comparingByValue())
                            .map(maxEntry -> new BestTeaYear(maxEntry.getKey(), teaType));
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public static List<TeaType> getTeaTypesInYear(List<TeaBag> teaBags, int year) {
        if (teaBags == null || teaBags.isEmpty()) {
            return new ArrayList<>();
        }

        return teaBags.stream()
                .filter(teaBag -> teaBag.year() == year)
                .map(TeaBag::name)
                .distinct()
                .toList();
    }

    public static List<HaviestTeaBag> getHaviestTeaBag(List<TeaBag> teaBags) {
        if (teaBags == null || teaBags.isEmpty()) {
            return Collections.emptyList();
        }

        return teaBags.stream()
                .collect(Collectors.groupingBy(TeaBag::name,
                        Collectors.maxBy(Comparator.comparingInt(TeaBag::weight))
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue().isPresent())
                .map(entry -> new HaviestTeaBag(entry.getValue().get().weight(), entry.getKey()))
                .toList();
    }

}
