package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.GameWalkthrough;
import ru.vsu.amm.java.service.GameWalkthroughStatsService;
import ru.vsu.amm.java.util.FileWorker;

import java.util.List;

public class GameWalkthroughDemo {
    private static final String PATH = "app/G91/enokyan.gera/lab4/walkthroughes.txt";

    public static void main(String[] args) {
        FileWorker.generateFile(PATH, 500);

        List<GameWalkthrough> walkthroughes = FileWorker.getFromFile(PATH);

        System.out.println("Highest rated genre: " + GameWalkthroughStatsService.findHighestRatedGenre(walkthroughes) + '\n');

        System.out.println("Month with most playtime: " + GameWalkthroughStatsService.findMonthWithMostPlaytime(walkthroughes) + '\n');

        System.out.println("Games with multiple walkthroughes:");
        GameWalkthroughStatsService.findAllMultipleWalkthroughGames(walkthroughes).forEach(System.out::println);
    }
}