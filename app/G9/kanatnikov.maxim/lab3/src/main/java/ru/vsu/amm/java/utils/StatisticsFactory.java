package ru.vsu.amm.java.utils;

import ru.vsu.amm.java.entity.Statistics;
import ru.vsu.amm.java.enums.Article;
import ru.vsu.amm.java.enums.Name;
import ru.vsu.amm.java.enums.Result;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StatisticsFactory {
    private final static Random RANDOM = new Random();
    private final static Name[] NAMES = Name.values();
    private final static int DAYS = 3000;
    private final static Article[] ARTICLES = Article.values();
    private final static Result[] RESULTS = Result.values();

    private StatisticsFactory() {
    }

    public static Statistics generateStatistics() {
        Name defendantName = NAMES[RANDOM.nextInt(NAMES.length)];
        Name plaintiffName = NAMES[RANDOM.nextInt(NAMES.length)];
        LocalDate date = LocalDate.now().minusDays(DAYS);
        Article article = ARTICLES[RANDOM.nextInt(ARTICLES.length)];
        Result result = RESULTS[RANDOM.nextInt(RESULTS.length)];
        return new Statistics(defendantName, plaintiffName, date, article, result);
    }

    public static List<Statistics> generateStatistics(int count) {

        List<Statistics> statisticsList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            var statistics = generateStatistics();
            statisticsList.add(statistics);
        }
        return statisticsList;
    }
}
