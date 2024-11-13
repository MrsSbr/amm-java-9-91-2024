package ru.vsu.amm.java.main;


import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Statistic {
    public static List<String> bestSellingGenreGames(List<GameRecord> listOfSell) {
        Set<Genre> genres = listOfSell.stream()
                .map(game -> game.getGenre())
                .collect(Collectors.toSet());
        Long max = genres.stream()
                .map(genre -> listOfSell.stream()
                        .filter(game -> game.getGenre().equals(genre))
                        .count())
                .max(Comparator.comparingLong(Long::longValue))
                .orElse(null);
        Genre popularGenre = genres.stream()
                .filter(genre -> listOfSell.stream()
                        .filter(game -> game.getGenre().equals(genre))
                        .count() == max)
                .findFirst()
                .orElse(null);
        List<String> result = listOfSell.stream()
                .filter(game -> game.getGenre().equals(popularGenre))
                .map(game -> game.getName())
                .distinct()
                .collect(Collectors.toList());
        return result;
    }

    public static List<Month> mostSuccessMonth(List<GameRecord> listOfSell) {
        List<Month> months = List.of(Month.values());
        List<Long> sales = months.stream()
                .map(month -> listOfSell.stream()
                        .filter(game -> game.getDate().getMonth() == month)
                        .mapToLong(x -> x.getPrice())
                        .sum())
                .toList();
        Long max = sales.stream().max(Comparator.comparingLong(Long::longValue)).orElse(0L);
        List<Month> successMonth = months.stream()
                .filter(month -> (sales.get(month.getValue() - 1) == max && max != 0))
                .toList();
        return successMonth;
    }

    public static List<String> nameGame(List<GameRecord> listOfSell) {
        Set<String> names = listOfSell.stream()
                .map(game -> game.getName())
                .collect(Collectors.toSet());
        LocalDate date = LocalDate.now().minusMonths(3);
        var namesGame = names.stream()
                .filter(n -> listOfSell.stream()
                        .filter(game -> game.getName() == n && game.getDate().isAfter(date))
                        .count() == 0)
                .toList();
        return namesGame;
    }
}