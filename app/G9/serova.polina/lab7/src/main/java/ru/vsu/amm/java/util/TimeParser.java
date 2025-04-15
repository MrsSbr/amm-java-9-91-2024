package ru.vsu.amm.java.util;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TimeParser {
    public static List<LocalTime> parseTimes(String[] times) {
        return Arrays.stream(times)
                .filter(it -> it != null && !it.isEmpty())
                .map(LocalTime::parse)
                .collect(Collectors.toList());
    }
}
