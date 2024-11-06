package ru.vsu.amm.java.main;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import java.util.stream.Collectors;

public class Statistic {
    public static ArrayList<GameRecord> createList(int size) {
        ArrayList<GameRecord> list = new ArrayList<>(7283);
        for (int i = 0; i < size; ++i) {
            list.add(RandomGameGenerator.Generate());
        }
        return list;
    }

    public static List<String> bestSellingGenreGames(ArrayList<GameRecord> listOfSell) {
        Set<Genre> genres = listOfSell.stream().
                map(game -> game.getGenre()).
                collect(Collectors.toSet());
        int max = genres.stream()
                .map(gen -> listOfSell.stream()
                        .filter(game -> game.getGenre().equals(gen))
                        .count())
                .max(Comparator.comparingLong(Long::longValue))
                .get()
                .intValue();
        Genre genre = genres.stream()
                .filter(gen -> listOfSell.stream()
                        .filter(game -> game.getGenre().equals(gen))
                        .count() == max)
                .findFirst().get();
        List<String> result = listOfSell.stream()
                .filter(game -> game.getGenre().equals(genre))
                .map(game -> game.getName())
                .distinct()
                .collect(Collectors.toList());
        return result;
    }

    public static int mostSuccessMonth(ArrayList<GameRecord> listOfSell) {
        ArrayList<Integer> months = new ArrayList<Integer>(Arrays.asList( 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        List<Integer> sales = months.stream()
                .map(mon -> listOfSell.stream()
                        .filter(game -> game.getDate().getMonthValue() == mon)
                        .mapToInt(x -> x.getPrice())
                        .sum())
                .collect(Collectors.toList());
        int max = sales.stream().max(Comparator.comparingInt(Integer::intValue)).get();
        int month = months.stream()
                .filter(mon -> sales.get(mon) == max)
                .findFirst().get();
        return month;
    }

    public static boolean recentlyDate(LocalDate x, LocalDate y) {
        return x.getYear() == y.getYear() &&
                (x.getMonthValue() - y.getMonthValue() < 3 || x.getMonthValue() - y.getMonthValue() == 3
                        && x.getDayOfMonth() >= y.getDayOfMonth());
    }

    public static String nameGame(ArrayList<GameRecord> listOfSell) {
        Set<String> names = listOfSell.stream()
                .map(game -> game.getName())
                .collect(Collectors.toSet());
        LocalDate today = java.time.LocalDate.now();
        String name = names.stream()
                .filter(n -> listOfSell.stream()
                        .filter(game -> game.getName() == n && recentlyDate(today, game.getDate()))
                        .count() == 0)
                .findFirst()
                .orElse("none");
        return name;
    }

    public static void main(String[] args) {
        int size = 7283;
        ArrayList<GameRecord> list = createList(size);
        bestSellingGenreGames(list).stream().forEach(game -> System.out.println(game));
        LocalDate date = LocalDate.of(1, mostSuccessMonth(list), 1);
        System.out.println(date.getMonth());
        System.out.println(nameGame(list));
    }
}