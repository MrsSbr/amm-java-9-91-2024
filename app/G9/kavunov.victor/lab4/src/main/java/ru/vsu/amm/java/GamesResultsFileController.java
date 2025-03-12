package ru.vsu.amm.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GamesResultsFileController {
    public static final int HOME_TEAM_INDEX = 0;
    public static final int AWAY_TEAM_INDEX = 1;
    public static final int GOALS_INDEX = 2;
    private static final Logger logger = Logger.getLogger(GamesResultsFileController.class.getName());

    public static List<GameResult> readFromFile(String path) throws IOException {
        List<GameResult> gamesResults = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(";");
                String[] goals = tokens[GOALS_INDEX].split(":");
                gamesResults.add(new GameResult(
                        Team.valueOf(tokens[HOME_TEAM_INDEX]),
                        Team.valueOf(tokens[AWAY_TEAM_INDEX]),
                        Integer.parseInt(goals[0]),
                        Integer.parseInt(goals[1])));
            }
        } catch (IOException | IllegalArgumentException e) {
            logger.log(Level.SEVERE, e.toString());
            throw e;
        }
        return gamesResults;
    }

    public static void writeToFile(String path, List<GameResult> gamesResults) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (GameResult gameResult : gamesResults) {
                String line = String.format(
                        "%s;%s;%d:%d\n",
                        gameResult.homeTeam(),
                        gameResult.awayTeam(),
                        gameResult.homeTeamGoals(),
                        gameResult.awayTeamGoals());
                bw.write(line);
            }
        } catch (IOException | IllegalArgumentException e) {
            logger.log(Level.SEVERE, e.toString());
            throw e;
        }
    }
}
