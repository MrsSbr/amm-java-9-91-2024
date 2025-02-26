package ru.vsu.amm.java.utils;

import ru.vsu.amm.java.entities.DateRange;
import ru.vsu.amm.java.entities.PastryRecord;
import ru.vsu.amm.java.enums.PastryName;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PastryGenerator {

    private static final PastryName[] pastryNames = PastryName.values();

    public static PastryRecord generateOnePastryRecord(LocalDate currentDate) {
        Random rand = new Random();

        PastryRecord pastryRecord = new PastryRecord(
                currentDate,
                pastryNames[rand.nextInt(pastryNames.length)],
                Math.abs(rand.nextInt() % 100),
                Math.abs(rand.nextInt() % 10000)
        );

        return pastryRecord;
    }

    public static List generateManyPastryRecords() {
        Random rand = new Random();
        DateRange dateRange = new DateRange(LocalDate.now().minusDays(Math.abs(rand.nextInt() % 50)), LocalDate.now());

        List pastryRecords = new ArrayList<PastryRecord>();

        for (LocalDate crntDay = dateRange.getStartDate();
             crntDay.isBefore(dateRange.getEndDate());
             crntDay = crntDay.plusDays(1)) {
            int countPastries = Math.abs(rand.nextInt() % 10);

            LocalDate currentDate = crntDay;

            Stream<PastryRecord> pastryRecordsStream = IntStream.range(0, countPastries)
                    .mapToObj(i -> generateOnePastryRecord(currentDate));

            pastryRecords.addAll(pastryRecordsStream.toList());
        }

        return pastryRecords;
    }
}
