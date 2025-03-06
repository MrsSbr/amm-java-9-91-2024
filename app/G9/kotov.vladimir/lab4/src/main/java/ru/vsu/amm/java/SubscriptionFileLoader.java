package ru.vsu.amm.java;


import ru.vsu.amm.java.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class SubscriptionFileLoader {
    private final SubscriptionParser parser;
    private final Logger logger;

    public SubscriptionFileLoader() {
        this.parser = new SubscriptionParser();
        this.logger = Logger.getLogger(SubscriptionFileLoader.class.getName());
    }

    public List<SubscriberRecord> loadFile(String filePath) {
        List<SubscriberRecord> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    records.add(parser.parseLine(line));
                } catch (IllegalArgumentException e) {
                    logger.severe("ERRОшибка при разборе строки: \"" + line + "\". " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            logger.severe("Файл не найден: " + filePath + ". " + e.getMessage());
            return new ArrayList<>();
        }
        return records;
    }
}
