package ru.vsu.amm.java.util;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class TimeParser {
    public static List<LocalTime> parseTimes(String[] times) {
        return Arrays.stream(times)
                .filter(it -> it != null && !it.isEmpty())
                .map(LocalTime::parse)
                .toList();
    }

    public static List<LocalTime> parseTimes(Time[] times) {
        return Arrays.stream(times)
                .map(Time::toLocalTime)
                .toList();
    }
}
