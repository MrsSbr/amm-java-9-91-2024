package ru.vsu.amm.java.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerInitializer {

    public static Logger initializeLogger(String patternPath, String className) {

        Logger logger = Logger.getLogger(className);

        try {

            FileHandler fileHandler = new FileHandler(patternPath);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);

        }
        catch (IOException e) {

            logger.log(Level.SEVERE,
                    String.format("Creation of log file for %s failed with an error: ", className),
                    e);

        }

        return logger;

    }
}
