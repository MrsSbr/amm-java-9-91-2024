package ru.vsu.amm.java.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerInitializer {

    public static Logger initializeLogger(String logFilePath, String className) {

        Logger logger = Logger.getLogger(className);

        try {

            File logFile = new File(logFilePath);
            File parentDir = logFile.getParentFile();

            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            FileHandler fileHandler = new FileHandler(logFilePath, true);
            fileHandler.setFormatter(new SimpleFormatter());

            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);

        }
        catch (IOException e) {

            logger.log(Level.SEVERE,
                    String.format("Failed to initialize logger for %s because of an error: ", className),
                    e);

        } catch (SecurityException e) {

            logger.log(Level.SEVERE,
                    String.format("Failed to create logger for %s because of a security error: ", className),
                    e);

        }

        return logger;

    }
}
