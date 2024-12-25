package ru.vsu.amm.java;
import java.util.ArrayList;
import java.util.List;

public class FarmApplication {
    public static void main(String[] args) {
        DateGenerator dateGenerator = new DateGenerator();
        List<DayStatistic> statistics = new ArrayList<>
                (dateGenerator.generateStatistic(100));

        System.out.println("Best month:");
        var bestMonth = FarmService.findBestMonthOrNull(statistics);
        if(bestMonth == null) {
            System.out.println("None");
        } else {
            System.out.println(bestMonth);
        }
        System.out.println(FarmService.findBestMonthOrNull(statistics) + "\n");
        System.out.println("Average milk per week:");
        System.out.println(FarmService.averageMilkPerWeek(statistics) + "\n");

        System.out.println("Total feed consumed: ");
        System.out.println(FarmService.totalFeedConsumed(statistics));
    }

}
