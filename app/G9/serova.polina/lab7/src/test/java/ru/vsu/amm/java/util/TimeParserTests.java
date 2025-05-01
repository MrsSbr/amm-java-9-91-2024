package ru.vsu.amm.java.util;

import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimeParserTests {

    @Test
    public void parseTimes_fromStringArray_shouldParseValidTimes() {
        String[] times = {"09:00", "14:30", "23:59"};
        List<LocalTime> expected = Arrays.asList(
                LocalTime.of(9, 0),
                LocalTime.of(14, 30),
                LocalTime.of(23, 59)
        );

        List<LocalTime> result = TimeParser.parseTimes(times);
        assertEquals(expected, result);
    }

    @Test
    public void parseTimes_fromStringArray_shouldSkipNullAndEmpty() {
        String[] times = {"08:00", null, "", "12:15"};
        List<LocalTime> expected = Arrays.asList(
                LocalTime.of(8, 0),
                LocalTime.of(12, 15)
        );

        List<LocalTime> result = TimeParser.parseTimes(times);
        assertEquals(expected, result);
    }

    @Test
    public void parseTimes_fromStringArray_shouldReturnEmptyList() {
        String[] times = {null, ""};
        List<LocalTime> result = TimeParser.parseTimes(times);
        assertTrue(result.isEmpty());
    }

    @Test
    public void parseTimes_fromTimeArray_shouldConvertSqlTimes() {
        Time[] times = {
                Time.valueOf("10:00:00"),
                Time.valueOf("16:45:00")
        };
        List<LocalTime> expected = Arrays.asList(
                LocalTime.of(10, 0),
                LocalTime.of(16, 45)
        );

        List<LocalTime> result = TimeParser.parseTimes(times);
        assertEquals(expected, result);
    }

    @Test
    public void parseTimes_fromTimeArray_shouldReturnEmptyListForEmptyArray() {
        Time[] times = {};
        List<LocalTime> result = TimeParser.parseTimes(times);
        assertTrue(result.isEmpty());
    }
}
