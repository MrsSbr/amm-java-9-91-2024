package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.TeaBag;
import ru.vsu.amm.java.enums.TeaType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TeaBagService {

    public static int[] getBestYearsOfType(List<TeaBag> teaBags) {
        if (teaBags == null || teaBags.isEmpty()) {
            return new int[]{};
        }
        TeaType[] types = TeaType.values();
        return Arrays.stream(types)
                .map(teaType -> teaBags.stream()
                        .filter(TeaBag -> TeaBag.name().equals(teaType))
                        .collect(Collectors.groupingBy(TeaBag::year,
                                        Collectors.summingInt(TeaBag::weight)
                                )
                        )
                        .entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .get()
                        .getKey()
                )
                .mapToInt(Integer::intValue)
                .toArray();

    }

    public static List<TeaType> getTeaTypesInYear(List<TeaBag> teaBags, int year) {
        return teaBags.stream()
                .filter(teaBag -> teaBag.year() == year)
                .map(TeaBag::name)
                .distinct()
                .toList();
    }

    public static int[] getHaviestTeaBag(List<TeaBag> teaBags) {
        if (teaBags == null || teaBags.isEmpty()) {
            return new int[]{};
        }
        TeaType[] types = TeaType.values();
        return Arrays.stream(types)
                .map(teaType -> teaBags.stream()
                        .filter(TeaBag -> TeaBag.name().equals(teaType))
                        .map(TeaBag::weight)
                        .max(Integer::compareTo)
                        .get()
                )
                .mapToInt(Integer::intValue)
                .toArray();
    }

}
