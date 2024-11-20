package ru.vsu.amm.java.main;


import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Statistic {
    public static List<String> bestSellingGenreGames(List<GameRecord> listOfSell) {
        if(listOfSell == null || listOfSell.isEmpty())
            return new ArrayList<>();
        Set<Genre> genres = listOfSell.stream()
                .map(GameRecord::getGenre)
                .collect(Collectors.toSet());
        Long max = genres.stream()
                .map(genre -> listOfSell.stream()
                        .filter(game -> game.getGenre().equals(genre))
                        .count())
                .max(Comparator.comparingLong(Long::longValue))
                .orElse(0L);
        List<Genre> popularGenre = genres.stream()
                .filter(genre -> listOfSell.stream()
                        .filter(game -> game.getGenre().equals(genre))
                        .count() == max)
                .toList();
        return listOfSell.stream()
                .filter(game -> popularGenre.contains(game.getGenre()))
                .map(GameRecord::getName)
                .distinct()
                .toList();
    }

    public static List<Month> mostSuccessMonth(List<GameRecord> listOfSell) {
        if(listOfSell == null || listOfSell.isEmpty())
            return new ArrayList<>();
        List<Month> months = List.of(Month.values());
        List<Long> sales = months.stream()
                .map(month -> listOfSell.stream()
                        .filter(game -> game.getDate().getMonth() == month)
                        .mapToLong(GameRecord::getPrice)
                        .sum())
                .toList();
        Long max = sales.stream().max(Comparator.comparingLong(Long::longValue)).orElse(0L);
        return months.stream()
                .filter(month -> (sales.get(month.getValue() - 1).equals(max)))
                .toList();
    }

    public static List<String> nameGame(List<GameRecord> listOfSell) {
        if(listOfSell == null || listOfSell.isEmpty())
            return new ArrayList<>();
        Set<String> names = listOfSell.stream()
                .map(GameRecord::getName)
                .collect(Collectors.toSet());
        LocalDate date = LocalDate.now().minusMonths(3);
        return names.stream()
                .filter(name -> listOfSell.stream()
                        .noneMatch(game -> game.getName().equals(name) && game.getDate().isAfter(date)))
                .toList();
    }
}