package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.EggProductionRecord;
import ru.vsu.amm.java.enums.BirdType;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public final class EggProductionFactory {
    private final static Random rnd = new Random();

    EggProductionFactory() {
    }

    public static EggProductionRecord generateRecord(){
        int year = rnd.nextInt(2023,2024); //последние 3 года?
        int dayOfYear = rnd.nextInt(1, Year.isLeap(year) ? 367 : 366);
        LocalDate date = LocalDate.ofYearDay(year, dayOfYear);
        //для теста
        //LocalDate today = LocalDate.now();
        //LocalDate threeWeeksAgo = today.minus(3, ChronoUnit.WEEKS);
        //LocalDate date = threeWeeksAgo.plusDays(rnd.nextInt(21));  // Генерация случайных дат в последние три недели
        var birdTypes = BirdType.values();
        BirdType birdType = birdTypes[rnd.nextInt(birdTypes.length)];
        int eggsCount = rnd.nextInt(20, 51); //до 50 яиц?
        return new EggProductionRecord(date, birdType, eggsCount);
    }

    public static List<EggProductionRecord> generateRecords(int n) {
        return IntStream.range(0, n)
                .mapToObj(tmp -> generateRecord())
                .collect(Collectors.toList());
    }
}
