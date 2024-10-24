package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.GameWalkthrough;
import ru.vsu.amm.java.service.GameWalkthroughStatsService;
import ru.vsu.amm.java.util.FileWorker;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class GameWalkthroughDemo {
    private static final Logger logger = Logger.getLogger(GameWalkthroughDemo.class.getName());

    private static final String PATH = "app/G91/enokyan.gera/lab4/src/main/java/ru/vsu/amm/java/resources/walkthroughes.txt";

    static {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("app/G91/enokyan.gera/lab4/src/main/java/ru/vsu/amm/java/resources/log.config"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "failed to load logger config");
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            FileWorker.generateFile(PATH, 500);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Unable to generate file.");
            throw new RuntimeException(e);
        }

        List<GameWalkthrough> walkthroughes = null;
        try {
            walkthroughes = FileWorker.getFromFile(PATH);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Null pointer was passed to method.");
            throw new RuntimeException(e);
        }

        try {
            System.out.println("Highest rated genre: " + GameWalkthroughStatsService.findHighestRatedGenre(walkthroughes) + '\n');
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "Null pointer was passed to method.");
            throw new RuntimeException(e);
        }

        try {
            System.out.println("Month with most playtime: " + GameWalkthroughStatsService.findMonthWithMostPlaytime(walkthroughes) + '\n');
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "Null pointer was passed to method.");
            throw new RuntimeException(e);
        }

        try {
            System.out.println("Games with multiple walkthroughes:");
            GameWalkthroughStatsService.findAllMultipleWalkthroughGames(walkthroughes).forEach(System.out::println);
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "Null pointer was passed to method.");
            throw new RuntimeException(e);
        }
    }
}