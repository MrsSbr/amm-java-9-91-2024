package ru.vsu.amm.java.logger;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerConfig {
    public static final String PATH =
            "app/G91/dolzhenkov.dmitrii/lab4/src/main/java/ru/vsu/amm/java/log/application.log";

    public static void configure() throws IOException {
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.ALL);

        for (Handler handler : rootLogger.getHandlers()) {
            rootLogger.removeHandler(handler);
        }

        Handler consoleHandler = createHandler(new ConsoleHandler(), Level.INFO);
        Handler fileHandler = createHandler(new FileHandler(PATH, true), Level.ALL);

        rootLogger.addHandler(consoleHandler);
        rootLogger.addHandler(fileHandler);
    }

    private static Handler createHandler(Handler handler, Level level) {
        handler.setLevel(level);
        handler.setFormatter(new CustomLoggerFormatter());
        return handler;
    }
}
