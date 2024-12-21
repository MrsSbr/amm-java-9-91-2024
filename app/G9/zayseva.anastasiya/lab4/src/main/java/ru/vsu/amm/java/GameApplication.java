package ru.vsu.amm.java;

import java.util.List;

public class GameApplication {
    private static final String PATH = "C:\\Users\\User\\IdeaProjects\\amm-java-9-91-2024\\app\\G9\\zayseva.anastasiya\\lab4\\src\\main\\java\\ru\\vsu\\amm\\java\\data\\test.txt";

    public static void main(String[] args) {
        List<GameRecord> records = FileHandler.readRecordsFromFile(PATH);

        Genre topGenre = GameService.getTopGenre(records);
        System.out.println("Top genre: " + topGenre);

        var mostTimeSpentMonth = GameService.getMonthWithMostHours(records);
        System.out.println("Month with most hours spent: " + mostTimeSpentMonth);

        List<String> repeatedGames = GameService.getRepeatedGames(records);
        System.out.println("Repeated games: " + repeatedGames);
    }
}
