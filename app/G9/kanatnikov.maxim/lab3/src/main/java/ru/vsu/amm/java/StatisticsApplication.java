package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.Statistics;
import ru.vsu.amm.java.service.StatisticsService;
import ru.vsu.amm.java.utils.StatisticsFactory;

import java.util.List;

public class StatisticsApplication {
    public static void main(String[] args) {
        final int count = 5780;
        List<Statistics> statistics = StatisticsFactory.generateStatistics(count);

        System.out.println("List of people convicted more than once last three years");
        StatisticsService.getPeopleConvictedMoreThanOnceLastThreeYears(statistics)
                .forEach(System.out::println);

        System.out.println("Year with best percentage of acquitted");
        StatisticsService.yearWithBestPercentageOfAcquitted(statistics)
                .forEach(System.out::println);

        System.out.println("List of people who was plaintiff and defendant");
        StatisticsService.getPeopleAsPlaintiffsAndDefendants(statistics)
                .forEach(System.out::println);
    }
}