package ru.vsu.amm.java.service;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logg {
    public static Logger logger;

    static {
        try {
            logger = createFileLogAndLog();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Logger createFileLogAndLog() throws IOException {
        Logger logger = Logger.getLogger(Logg.class.getName());

        File file = new File("log.txt");

        if (file.exists()) {
            file.delete();
        }

        FileHandler fileHandler = new FileHandler(file.getName(), true);
        fileHandler.setFormatter(new SimpleFormatter());

        logger.addHandler(fileHandler);
        logger.setLevel(Level.ALL);

        return logger;
    }
}