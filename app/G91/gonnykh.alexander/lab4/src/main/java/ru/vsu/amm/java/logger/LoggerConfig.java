package ru.vsu.amm.java.logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;

public class LoggerConfig {
    public static final String PATH =
            "app/G91/gonnykh.alexander/lab4/src/main/java/ru/vsu/amm/java/log/application.log";

    public static void configure() throws IOException {
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.ALL);

        for (Handler handler : rootLogger.getHandlers()) {
            rootLogger.removeHandler(handler);
        }

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.INFO);
        consoleHandler.setFormatter(new CustomLoggerFormatter());

        File logFile = new File(PATH);
        logFile.getParentFile().mkdirs();
        FileHandler fileHandler = new FileHandler(logFile.getPath(), true);

        fileHandler.setLevel(Level.ALL);
        fileHandler.setFormatter(new CustomLoggerFormatter());

        rootLogger.addHandler(consoleHandler);
        rootLogger.addHandler(fileHandler);
    }

}
