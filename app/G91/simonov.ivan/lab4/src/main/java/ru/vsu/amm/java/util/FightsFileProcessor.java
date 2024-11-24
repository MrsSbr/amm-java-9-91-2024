package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.Fight;
import ru.vsu.amm.java.enums.Fatality;
import ru.vsu.amm.java.enums.Hero;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FightsFileProcessor {

    private static final Logger LOGGER = Logger.getLogger(FightsFileProcessor.class.getName());
    private static final Integer TOURNAMENT_NUM_INDEX = 0;
    private static final Integer DATE_INDEX = 1;
    private static final Integer PARTICIPANT1_INDEX = 2;
    private static final Integer PARTICIPANT2_INDEX = 3;
    private static final Integer WINNER_INDEX = 4;
    private static final Integer FATALITY_INDEX = 5;

    static {
        try {
            FileHandler fileHandler = new FileHandler("app/G91/simonov.ivan/lab4/src/main/java"
                    + "/ru/vsu/amm/java/logs/fights-file-processor-logs.log");
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setUseParentHandlers(false);
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Creation of log file for FightsFileProcessor failed with an error: ", e);
        }
    }

    public static void writeFights(List<Fight> fights, String filePath, boolean isAppend) {

        LOGGER.log(Level.INFO, String.format("Detected try to write data to the file %s", filePath));

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, isAppend))) {

            for (Fight fight : fights) {
                bufferedWriter.write(String.format("%d %s %s %s %s %s\n",
                        fight.tournamentNum(),
                        fight.date(),
                        fight.participant1(),
                        fight.participant2(),
                        fight.winner(),
                        fight.fatality()));
            }
            LOGGER.log(Level.INFO, String.format("Writing data to the file %s completed successfully", filePath));
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format("Writing data to the file %s failed with an error:", filePath), e);
        }
    }

    public static List<Fight> readFights(String filePath) {

        LOGGER.log(Level.INFO, String.format("Detected try to read data from the file %s", filePath));
        List<Fight> readData = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splits = line.split(" ");
                readData.add(new Fight(Integer.parseInt(splits[TOURNAMENT_NUM_INDEX]),
                        LocalDate.parse(splits[DATE_INDEX]),
                        Hero.valueOf(splits[PARTICIPANT1_INDEX]),
                        Hero.valueOf(splits[PARTICIPANT2_INDEX]),
                        Hero.valueOf(splits[WINNER_INDEX]),
                        Fatality.valueOf(splits[FATALITY_INDEX])
                ));
            }
            LOGGER.log(Level.INFO, String.format("Reading data from the file %s completed successfully", filePath));
        }
        catch (IOException | IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, String.format("Reading data "
                    + "from the file %s failed with an error: ", filePath), e);
            return new ArrayList<>();
        }
        return readData;
    }
}
