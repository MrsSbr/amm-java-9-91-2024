package ru.vsu.amm.java;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DateGenerator {
    public List<DayStatistic> generateStatistic(int count) {
        List<DayStatistic> statistics = new ArrayList<>();
        Random random = new Random();
        LocalDate startDate = LocalDate.of(2018,1,1);

        for(int i = 0; i < count; i++){
            LocalDate date = startDate.plusDays(i);
            double feedConsumed = 5 + random.nextDouble() * 20;
            double milkProduced = 3 +random.nextDouble() * 10;
            statistics.add(new DayStatistic(date, feedConsumed, milkProduced));
        }
        return statistics;
    }

}
