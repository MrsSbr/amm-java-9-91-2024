package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RaceResultFactory {
    private static final Random rand = new Random();

    private static LocalDate generateRandomDate(LocalDate minDate, LocalDate maxDate) {
        long minDay = minDate.toEpochDay();
        long maxDay = maxDate.toEpochDay();
        long diff = maxDay - minDay;
        long resultDay = minDay + rand.nextInt((int) diff);
        return LocalDate.ofEpochDay(resultDay);
    }

    public static RaceResult generateRandomRaceResult() {
        int participantNum = rand.nextInt(1, 1000);
        LocalDate tenYearsAgo = LocalDate.now().minusYears(10);
        LocalDate raceDate = generateRandomDate(tenYearsAgo, LocalDate.now());
        int place = rand.nextInt(1, 100);
        return new RaceResult(participantNum, raceDate, place);
    }

    public static List<RaceResult> generateRandomList(int numOfRecords) {
        return IntStream.range(0, numOfRecords)
                .mapToObj(_ -> generateRandomRaceResult())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
