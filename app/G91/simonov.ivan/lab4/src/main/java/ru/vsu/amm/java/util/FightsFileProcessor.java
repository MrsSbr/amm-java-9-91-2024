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
import java.util.logging.Level;
import java.util.logging.Logger;

public class FightsFileProcessor {

    private static final Logger LOGGER = Logger.getLogger(FightsFileProcessor.class.getName());

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
            LOGGER.log(Level.SEVERE, String.format("Writing data to the file %s failed with an error \"%s\"", filePath, e));
        }
    }

    public static List<Fight> readFights(String filePath) {

        LOGGER.log(Level.INFO, String.format("Detected try to read data from the file %s", filePath));
        List<Fight> readData = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splits = line.split(" ");
                readData.add(new Fight(Integer.parseInt(splits[0]),
                        LocalDate.parse(splits[1]),
                        Hero.valueOf(splits[2]),
                        Hero.valueOf(splits[3]),
                        Hero.valueOf(splits[4]),
                        Fatality.valueOf(splits[5])
                ));
            }
            LOGGER.log(Level.INFO, String.format("Reading data from the file %s completed successfully", filePath));
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format("Reading data from the file %s failed with an error \"%s\"", filePath, e));
            return new ArrayList<>();
        }
        return readData;
    }
}
