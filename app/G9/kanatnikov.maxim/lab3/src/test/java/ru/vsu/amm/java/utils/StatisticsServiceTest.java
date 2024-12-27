package ru.vsu.amm.java.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.Statistics;
import ru.vsu.amm.java.enums.Article;
import ru.vsu.amm.java.enums.Name;
import ru.vsu.amm.java.enums.Result;
import ru.vsu.amm.java.service.StatisticsService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StatisticsServiceTest {
    private final List<Statistics> NULL_STATISTICS_LIST = null;
    private final List<Statistics> EMPTY_STATISTICS_LIST = new ArrayList<>();

    @Test
    public void testGetPeopleConvictedMoreThanOnceLastThreeYearsWithNull() {
        Set<Name> result = StatisticsService.getPeopleConvictedMoreThanOnceLastThreeYears(NULL_STATISTICS_LIST);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testGetPeopleConvictedMoreThanOnceLastThreeYearsWithEmpty() {
        Set<Name> result = StatisticsService.getPeopleConvictedMoreThanOnceLastThreeYears(EMPTY_STATISTICS_LIST);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testGetPeopleConvictedMoreThanOnceLastThreeYears() {
        List<Statistics> statisticsList = List.of(
                new Statistics(Name.KENNY, Name.ERIC, LocalDate.now().minusYears(2),
                        Article.MURDER, Result.CONVICTED),
                new Statistics(Name.KENNY, Name.KYLE, LocalDate.now().minusYears(1),
                        Article.KIDNAPPING, Result.CONVICTED),
                new Statistics(Name.KENNY, Name.JIMMY, LocalDate.now().minusYears(5),
                        Article.MURDER, Result.CONVICTED),
                new Statistics(Name.KYLE, Name.ERIC, LocalDate.now().minusYears(2),
                        Article.MURDER, Result.CONVICTED),
                new Statistics(Name.KYLE, Name.KENNY, LocalDate.now().minusDays(1),
                        Article.KIDNAPPING, Result.CONVICTED)
        );

        Set<Name> expected = Set.of(Name.KYLE, Name.KENNY);
        Set<Name> result = StatisticsService.getPeopleConvictedMoreThanOnceLastThreeYears(statisticsList);
        Assertions.assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }

    @Test
    public void testGetPeopleConvictedMoreThanOnceLastThreeYearsReturnEmptySet() {
        List<Statistics> statisticsList = List.of(
                new Statistics(Name.KENNY, Name.ERIC, LocalDate.now().minusYears(2),
                        Article.MURDER, Result.CONVICTED),
                new Statistics(Name.KENNY, Name.KYLE, LocalDate.now().minusYears(1),
                        Article.KIDNAPPING, Result.ACQUITTED),
                new Statistics(Name.KENNY, Name.JIMMY, LocalDate.now().minusYears(5),
                        Article.MURDER, Result.CONVICTED),
                new Statistics(Name.KYLE, Name.ERIC, LocalDate.now().minusYears(2),
                        Article.MURDER, Result.CONVICTED),
                new Statistics(Name.KYLE, Name.KENNY, LocalDate.now().minusYears(5),
                        Article.KIDNAPPING, Result.CONVICTED)
        );

        Set<Name> result = StatisticsService.getPeopleConvictedMoreThanOnceLastThreeYears(statisticsList);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testYearWithBestPercentageOfAcquittedWithNull() {
        Set<Integer> result = StatisticsService.yearWithBestPercentageOfAcquitted(NULL_STATISTICS_LIST);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testYearWithBestPercentageOfAcquittedWithEmpty() {
        Set<Integer> result = StatisticsService.yearWithBestPercentageOfAcquitted(EMPTY_STATISTICS_LIST);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testYearWithBestPercentageOfAcquitted50GreaterThan33() {
        List<Statistics> statisticsList = List.of(
                new Statistics(Name.KENNY, Name.ERIC, LocalDate.now().minusYears(2),
                        Article.MURDER, Result.ACQUITTED),
                new Statistics(Name.JIMMY, Name.STAN, LocalDate.now().minusYears(5),
                        Article.KIDNAPPING, Result.ACQUITTED),
                new Statistics(Name.KENNY, Name.JIMMY, LocalDate.now().minusYears(5),
                        Article.MURDER, Result.CONVICTED),
                new Statistics(Name.KYLE, Name.ERIC, LocalDate.now().minusYears(2),
                        Article.KIDNAPPING, Result.CONVICTED),
                new Statistics(Name.KYLE, Name.KENNY, LocalDate.now().minusYears(5),
                        Article.KIDNAPPING, Result.CONVICTED)
        );

        Set<Integer> expected = Set.of(LocalDate.now().minusYears(2).getYear());
        Set<Integer> result = StatisticsService.yearWithBestPercentageOfAcquitted(statisticsList);
        Assertions.assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }

    @Test
    public void testYearWithBestPercentageOfAcquitted100Percentage() {
        List<Statistics> statisticsList = List.of(
                new Statistics(Name.KENNY, Name.ERIC, LocalDate.now().minusYears(2),
                        Article.MURDER, Result.ACQUITTED),
                new Statistics(Name.KENNY, Name.KYLE, LocalDate.now().minusYears(1),
                        Article.KIDNAPPING, Result.ACQUITTED),
                new Statistics(Name.KENNY, Name.JIMMY, LocalDate.now().minusYears(5),
                        Article.MURDER, Result.CONVICTED),
                new Statistics(Name.KYLE, Name.ERIC, LocalDate.now().minusYears(2),
                        Article.MURDER, Result.ACQUITTED),
                new Statistics(Name.KYLE, Name.KENNY, LocalDate.now().minusYears(2),
                        Article.KIDNAPPING, Result.CONVICTED)
        );

        Set<Integer> expected = Set.of(LocalDate.now().minusYears(1).getYear());
        Set<Integer> result = StatisticsService.yearWithBestPercentageOfAcquitted(statisticsList);
        Assertions.assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }

    @Test
    public void testGetPeopleAsPlaintiffsAndDefendantsWithNull() {
        Set<Name> result = StatisticsService.getPeopleAsPlaintiffsAndDefendants(NULL_STATISTICS_LIST);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testGetPeopleAsPlaintiffsAndDefendantsWithEmpty() {
        Set<Name> result = StatisticsService.getPeopleAsPlaintiffsAndDefendants(EMPTY_STATISTICS_LIST);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testGetPeopleAsPlaintiffsAndDefendants() {
        List<Statistics> statisticsList = List.of(
                new Statistics(Name.KENNY, Name.ERIC, LocalDate.now().minusYears(2),
                        Article.MURDER, Result.CONVICTED),
                new Statistics(Name.KENNY, Name.KYLE, LocalDate.now().minusYears(1),
                        Article.KIDNAPPING, Result.ACQUITTED),
                new Statistics(Name.KENNY, Name.JIMMY, LocalDate.now().minusYears(5),
                        Article.MURDER, Result.CONVICTED),
                new Statistics(Name.KYLE, Name.ERIC, LocalDate.now().minusYears(2),
                        Article.MURDER, Result.CONVICTED),
                new Statistics(Name.KYLE, Name.KENNY, LocalDate.now().minusYears(5),
                        Article.KIDNAPPING, Result.CONVICTED)
        );

        Set<Name> expected = Set.of(Name.KENNY, Name.KYLE);
        Set<Name> result = StatisticsService.getPeopleAsPlaintiffsAndDefendants(statisticsList);
        Assertions.assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }

    @Test
    public void testGetPeopleAsPlaintiffsAndDefendantsReturnEmptyList() {
        List<Statistics> statisticsList = List.of(
                new Statistics(Name.KENNY, Name.ERIC, LocalDate.now().minusYears(2),
                        Article.MURDER, Result.CONVICTED),
                new Statistics(Name.KENNY, Name.KYLE, LocalDate.now().minusYears(1),
                        Article.KIDNAPPING, Result.ACQUITTED)
        );

        Set<Name> result = StatisticsService.getPeopleAsPlaintiffsAndDefendants(statisticsList);
        Assertions.assertTrue(result.isEmpty());
    }
}
