package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.OlympiadRecord;
import ru.vsu.amm.java.enums.Subjects;

import java.time.Year;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OlympiadRecordFactory {
    private final static Random RND = new Random();

    public static OlympiadRecord generateOlymiadRecord() {
        Year year = Year.of(RND.nextInt(2000, Year.now().getValue()));
        Subjects[] subjects = Subjects.values();
        Subjects subject = subjects[RND.nextInt(subjects.length)];
        int schoolClass = RND.nextInt(1, 12);
        List<String> winners = generateWinners();

        return new OlympiadRecord(year, subject, schoolClass, winners);
    }

    public static List<OlympiadRecord> generateOlymiadRecords(int n) {
        return IntStream.range(0, n)
                .mapToObj(x -> generateOlymiadRecord())
                .collect(Collectors.toList());
    }

    private static List<String> generateWinners() {
        return IntStream.range(0, 3)
                .mapToObj(i -> "Student" + RND.nextInt(1, 100))
                .collect(Collectors.toList());
    }
}
