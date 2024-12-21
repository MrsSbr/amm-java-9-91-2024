package ru.vsu.amm.java;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskFootballController {
    public static final String FILE_PATH = "app/G9/kavunov.victor/lab4/src/main/java/ru/vsu/amm/java/file.txt";
    private static final Logger logger = Logger.getLogger(GamesResultsFileController.class.getName());

    public static void main(String[] args) throws IOException {
        try {
            List<GameResult> gamesResults = GamesResultsFileController.readFromFile(FILE_PATH);

            System.out.println("Топ-3 команды");
            System.out.println(GamesResultsService.getTopThree(gamesResults));
            System.out.println("Команды, не пропустившие ни одного мяча в домашнем матче");
            System.out.println(GamesResultsService.getHomeTeamsWithoutAwayGoals(gamesResults));
            System.out.println("Список побеждённых команд для каждой команды ");
            System.out.println(GamesResultsService.getTeamsWithDefeatedTeams(gamesResults));

            GamesResultsFileController.writeToFile(FILE_PATH, gamesResults);
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.toString());
            System.out.println(e.getMessage());
        }
    }
}