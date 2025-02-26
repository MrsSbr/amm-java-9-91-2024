package ru.vsu.amm.java;

import ru.vsu.amm.java.entities.PastryRecord;
import ru.vsu.amm.java.services.FileIO;
import ru.vsu.amm.java.services.OutputConsole;
import ru.vsu.amm.java.services.PastryRecordAnalyze;

import java.io.IOException;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class PastryShopApp {
    private static final String PATH = "app/G9/jasser.daniel/lab4/pastryShop.txt";
    private static Logger logger = Logger.getLogger(PastryShopApp.class.getName());

    static {
        try {
            FileHandler fileHandler = new FileHandler("app/G9/jasser.daniel/lab4/pastryShop.logapp", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not initialize", e);
        }
    }

    public static void main(String[] args) {
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.INFO);
        for (var handler : rootLogger.getHandlers()) {
            handler.setLevel(Level.INFO);
        }

        logger.log(Level.INFO, "Starting.");
        FileIO.saveToFile(PATH);
        List<PastryRecord> pastryRecordList = FileIO.loadFromFile(PATH);
        if (pastryRecordList.isEmpty()) {
            logger.log(Level.SEVERE, "No pastries.");
            return;
        }
        PastryRecordAnalyze pastryRecordAnalyze = new PastryRecordAnalyze();

        logger.log(Level.INFO, "Analayzing");
        Month month = pastryRecordAnalyze.findMonthWithLowestIncome(pastryRecordList);
        OutputConsole.printMonthWithLowestIncome(month);

        Map<Month, Integer> map = pastryRecordAnalyze.findHeaviestPastryPerMonth(pastryRecordList);
        OutputConsole.printHeaviestPastryPerMonth(map);

        Map<Month, List<PastryRecord>> map2 = pastryRecordAnalyze.findAllRecordsByMonth(pastryRecordList);
        OutputConsole.printAllRecordsByMonth(map2);

        logger.log(Level.INFO, "Ending.");
    }
}