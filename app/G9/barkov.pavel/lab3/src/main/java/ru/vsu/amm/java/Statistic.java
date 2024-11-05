package ru.vsu.amm.java;

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

    public static List<GameRecord> bestSellingGenreGames(ArrayList<GameRecord> listOfSell) {

        Set<Genre> genres = listOfSell.stream().map(game -> game.getGenre()).collect(Collectors.toSet());
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
        List<GameRecord> result = listOfSell.stream()
                .filter(game -> game.getGenre().equals(genre))
                .collect(Collectors.toList());
        return result;
    }

    public static void main() {
        int size = 7283;
        ArrayList<GameRecord> list = createList(size);


    }
}