package ru.vsu.amm.java.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.vsu.amm.java.entities.Log;
import ru.vsu.amm.java.factories.LogCreator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogFileService {
    private static Logger logger;

    public LogFileService(Logger logger) {
        LogFileService.logger = logger;
    }

    public void createLogFile(String logFilePath, int logsCount) {
        var mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());

        var creator = new LogCreator();
        var logs = creator.create(logsCount);

        try (var writer = new FileWriter(logFilePath)) {
            for (var log : logs) {
                writer.write(mapper.writeValueAsString(log) + '\n');
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Log> getLogsFromFile(String logFilePath) {
        var mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());

        List<Log> logs;
        try (var reader = new BufferedReader(new FileReader(logFilePath))) {
            logs = reader.lines()
                    .map(line -> {
                        try {
                            return mapper.readValue(line, Log.class);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e.getMessage());
                        }
                    })
                    .toList();
        } catch (IOException | RuntimeException e) {
            logger.log(Level.WARNING, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return logs;
    }
}
