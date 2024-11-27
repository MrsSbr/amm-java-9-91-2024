package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.MoonshineData;
import ru.vsu.amm.java.enums.DrinkLabel;
import ru.vsu.amm.java.enums.Ingredient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

public class MoonshineService {
    private static final Logger logger = Logger.getLogger(MoonshineService.class.getName());
    private static final String LOG_PATH = System.getProperty("user.dir") + "\\moonshine-service.log";
    private static final String AVG_TIME_MSG = "Error in average time for ingredients method.\n";
    private static final String INGREDIENTS_MONTH_MSG = "Error in max month with ingredients method.\n.";
    private static final String TOTAL_VOLUME_MSG = "Error in total volume for ingredients method.\n";
    private static final String LOG_ERROR_MSG = "Error in logger creation for moonshine service.\n";

    static {
        try {
            FileHandler fileHandler = new FileHandler(LOG_PATH);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            logger.log(Level.SEVERE, LOG_ERROR_MSG, e);
        }
    }

    public static Map<Ingredient, Double> getAverageTimeForIngredients(List<MoonshineData> drinksList) {
        try {
            return drinksList.stream()
                    .flatMap(drinkData -> drinkData.ingredients().stream()
                            .map(x -> Map.entry(x, drinkData.makingTime())))
                    .collect(Collectors.groupingBy(Map.Entry::getKey,
                            Collectors.averagingDouble(Map.Entry::getValue)));
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, AVG_TIME_MSG, e);
            return new HashMap<>();
        }
    }

    public static int getMaxIngredientsMonth(List<MoonshineData> drinksList) {
        try {
            return drinksList.stream()
                    .collect(Collectors.groupingBy(x -> x.date().getMonthValue(),
                            Collectors.summingInt(x -> x.ingredients().size())))
                    .entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(0);
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, INGREDIENTS_MONTH_MSG, e);
            return 0;
        }
    }

    public static Map<DrinkLabel, Double> getTotalVolumeForEachDrink(List<MoonshineData> drinksList) {
        try {
            return drinksList.stream()
                    .collect(Collectors.groupingBy(MoonshineData::label,
                            Collectors.summingDouble(MoonshineData::volume)));
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, TOTAL_VOLUME_MSG, e);
            return new HashMap<>();
        }
    }
}
