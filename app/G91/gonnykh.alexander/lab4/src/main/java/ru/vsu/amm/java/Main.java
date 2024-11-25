package ru.vsu.amm.java;

import ru.vsu.amm.java.logger.LoggerConfig;
import ru.vsu.amm.java.records.CoffeeRecord;
import ru.vsu.amm.java.services.CoffeeService;
import ru.vsu.amm.java.util.CoffeeRecordGenerator;
import ru.vsu.amm.java.util.FileWorker;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    public static final String AVERAGE_PREPARATION_TIME =
            "среднее время приготовления: ";

    public static final String BEST_PRICE_TIME_RATIO_DRINK =
            "напиток с наилучшим соотношением цена/время: ";

    public static final String BUSIEST_HOUR_WEEKDAY =
            "самый загруженный час по будням: ";

    public static final String DRINKS_ORDERED_MOST_7AM_TO_12PM =
            "напитки, которые чаще всего заказывают с 7 до 12 утра:";

    public static final String PATH_TO_FILE =
            "app/G91/gonnykh.alexander/lab4/src/main/java/ru/vsu/amm/java/file/file.txt";

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {
        LoggerConfig.configure();

        List<CoffeeRecord> coffeeRecordList = CoffeeRecordGenerator.generateCoffeeRecords(10);

        FileWorker.saveToFile(coffeeRecordList, PATH_TO_FILE);

        coffeeRecordList = FileWorker.readInFile(PATH_TO_FILE);

        for (CoffeeRecord coffeeRecord : coffeeRecordList) {
            System.out.println(coffeeRecord);
        }

        System.out.println(AVERAGE_PREPARATION_TIME
                + CoffeeService.calculateAveragePreparationTime(coffeeRecordList).toString());

        System.out.println(BEST_PRICE_TIME_RATIO_DRINK
                + CoffeeService.findBestCoffeeRatio(coffeeRecordList).toString());

        System.out.println(BUSIEST_HOUR_WEEKDAY
                + CoffeeService.findBusiestHour(coffeeRecordList).toString());

        System.out.println(DRINKS_ORDERED_MOST_7AM_TO_12PM
                + CoffeeService.findCoffeeOrderedFrom7To12(coffeeRecordList).toString());
    }
}