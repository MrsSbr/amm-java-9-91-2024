package ru.vsu.amm.java;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.vsu.amm.java.services.LogFileService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final String LOG_FILE_PATH = "app/G9/golukovich.timofey/lab4/log_file.txt";
    private static final int LOGS_TO_CREATE_COUNT = 20;

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());
        try
        {
            var service = new LogFileService(logger);
            service.createLogFile(LOG_FILE_PATH, LOGS_TO_CREATE_COUNT);

            var logs = service.getLogsFromFile(LOG_FILE_PATH);

            var mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule());// todo падает при проблемах с файлом
            for (var log : logs) {
                System.out.println(mapper.writeValueAsString(log));
            }
        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage(), ex);
        }
    }
}