package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Statistic {

    public static ArrayList<GameRecord> createList(int size){
        ArrayList<GameRecord> list = new ArrayList<>(7283);
        for(int i = 0;i<size;++i) {
            list.add(RandomGameGenerator.Generate());
        }
        return list;
    }

    public static ArrayList<String> bestSellingGenreGames(ArrayList<GameRecord> listOfSell) {
        ArrayList<String> result =  listOfSell.stream().;
        return "da";
    }

    public static void main() {
        int size = 7283;
        ArrayList<GameRecord> list = createList(size);


    }
}