package ru.vsu.amm.java.services;

import ru.vsu.amm.java.entities.Log;
import ru.vsu.amm.java.factories.LogCreator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogFileService {
    private static Logger LOGGER;

    public LogFileService(Logger logger) {
        LOGGER = logger;
    }

    public void createLogFile(String logFilePath, int logsCount) {
        var creator = new LogCreator();
        var logs = creator.create(logsCount);
        try (ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream(logFilePath))) {
            ous.writeObject(logs);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Log> getLogsFromFile(String logFilePath) {
        List<Log> logs;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(logFilePath))) {
            logs = (ArrayList<Log>) ois.readObject();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return logs;
    }
}
