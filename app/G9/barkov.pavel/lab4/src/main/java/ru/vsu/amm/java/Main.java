package ru.vsu.amm.java;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private final static String PATH = "C:\\check\\test.txt";

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());
        WorkWithFile file_worker = new WorkWithFile(logger);
        List<Deal> deals = file_worker.read(PATH);
        try {
            Statistic.mostSuccessManager(deals).forEach(System.out::println);
            Statistic.incomeFromClients(deals).entrySet().forEach(System.out::println);
            Statistic.mostSuccessMonth(deals).forEach(System.out::println);
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }
}