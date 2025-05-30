package ru.vsu.amm.java.main;

import java.util.ArrayList;
import java.util.List;


public class Main {

    public static ArrayList<GameRecord> createList(int size) {
        ArrayList<GameRecord> list = new ArrayList<>(7283);
        for (int i = 0; i < size; ++i) {
            list.add(RandomGameGenerator.Generate());
        }
        return list;
    }

    public static void main(String[] args) {
        final int size = 7283;
        List<GameRecord> list = createList(size);
        Statistic.bestSellingGenreGames(list).forEach(System.out::println);
        Statistic.mostSuccessMonth(list).forEach(System.out::println);
        Statistic.nameGame(list).forEach(System.out::println);
    }
}
