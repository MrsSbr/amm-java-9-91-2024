package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RaceResultFactory {
    private static LocalDate generateRandomDate(LocalDate minDate, LocalDate maxDate) {
        Random rand = new Random();
        long minDay = minDate.toEpochDay();
        long maxDay = maxDate.toEpochDay();
        long diff = maxDay - minDay;
        long resultDay = minDay + rand.nextInt((int) diff);
        return LocalDate.ofEpochDay(resultDay);
    }

    public static RaceResult generateRandomRaceResult() {
        Random rand = new Random();
        int participantNum = rand.nextInt(1, 1000);
        LocalDate tenYearsAgo = LocalDate.now().minusYears(10);
        LocalDate raceDate = generateRandomDate(tenYearsAgo, LocalDate.now());
        int place = rand.nextInt(1, 100);
        return new RaceResult(participantNum, raceDate, place);
    }

    public static List<RaceResult> generateRandomList(int numOfRecords) {
        return IntStream.range(0, numOfRecords)
                .mapToObj(_ -> generateRandomRaceResult())
                .collect(Collectors.toList());
    }
}
