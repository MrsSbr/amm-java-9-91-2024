package ru.vsu.amm.java.services;

import ru.vsu.amm.java.entities.DateRange;
import ru.vsu.amm.java.entities.PastryRecord;
import ru.vsu.amm.java.enums.PastryName;
import ru.vsu.amm.java.utils.PastryGenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FileIO {

    public static Logger logger = Logger.getLogger(FileIO.class.getName());

    static {
        try {
            FileHandler fileHandler = new FileHandler("app/G9/jasser.daniel/lab4/pastryShop.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not initialize", e);
        }
    }

    public static void saveToFile(String path) {
        logger.log(Level.INFO, "Savings to " + path);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            List<PastryRecord> pastryRecordList = PastryGenerator.generateManyPastryRecords();
            for (PastryRecord pr : pastryRecordList) {
                writer.write(String.format("%s;%s;%s;%d%n", pr.getPastryName(), pr.getCompletionDate(), pr.getWeight(),
                        pr.getCost()));
            }
            logger.log(Level.INFO, "Saving completed.");
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error saving.", ex);
        }
    }

    public static List<PastryRecord> loadFromFile(String path) {
        logger.log(Level.INFO, "Loading from " + path);
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            List<PastryRecord> pastryRecordList = reader.lines()
                    .map(line -> {
                        String[] parts = line.split(";");
                        PastryName name = PastryName.valueOf(parts[0]);
                        LocalDate date_of_issue = LocalDate.parse(parts[1]);
                        int weight = Integer.parseInt(parts[2]);
                        int cost = Integer.parseInt(parts[3]);
                        return new PastryRecord(date_of_issue, name, weight, cost);
                    }).toList();
            logger.log(Level.INFO, "Saving completed.");
            return pastryRecordList;
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error loading.", ex);
            return List.of();
        }
    }
}
