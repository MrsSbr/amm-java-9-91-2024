package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.Medal;
import ru.vsu.amm.java.enums.Country;
import ru.vsu.amm.java.enums.KindOfSport;

import java.io.*;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class DataHandler {
    private static final Logger logger = Logger.getLogger(DataHandler.class.getName());
    private static final int CountryIndex = 0;
    private static final int KindOfSportIndex = 1;
    private static final int AthleteIndex = 2;
    private static final int PlaceIndex = 3;

    static {
        try {
            FileHandler fileHandler = new FileHandler("app/G9/shipilova.viktoria/lab4/medals_rec.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not initialize file handler for logger", e);
        }
    }

    public void saveToFile(String path) {
        logger.log(Level.INFO, "Saving medals to " + path);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            List<Medal>  medals = MedalFactory.generateMedal();
            for (Medal medal : medals) {
                writer.write(String.format("%s;%s;%s;%d%n", medal.getCountry(), medal.getKindOfSport(), medal.getAthlete(), medal.getPlace()));
            }
            logger.log(Level.INFO, "Saving completed successfully.");
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error saving medals.", ex);
        }
    }

    public List<Medal> loadFromFile(String path) {
        logger.log(Level.INFO, "Loading medals from " + path);
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            List<Medal> medals = reader.lines()
                    .map(line -> {
                        String[] parts = line.split(";");
                        Country country = Country.valueOf(parts[CountryIndex]);
                        KindOfSport kindOfSport = KindOfSport.valueOf(parts[KindOfSportIndex]);
                        String athlete = parts[AthleteIndex];
                        int place = Integer.parseInt(parts[PlaceIndex]);
                        return new Medal(country, kindOfSport, athlete, place);
                    }).toList();
            logger.log(Level.INFO, "Saving completed successfully.");
            return medals;
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error loading medals.", ex);
            return List.of();
        }
    }
}
