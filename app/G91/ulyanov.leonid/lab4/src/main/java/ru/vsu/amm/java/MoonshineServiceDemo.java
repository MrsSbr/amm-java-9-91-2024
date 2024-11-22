package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.MoonshineData;
import ru.vsu.amm.java.service.MoonshineService;
import ru.vsu.amm.java.util.FileManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MoonshineServiceDemo {
    private static final Logger LOGGER = Logger.getLogger(MoonshineServiceDemo.class.getName());
    private static final String PATH = System.getProperty("user.dir") + "\\test.txt";

    public static void main(String[] args) {
        try {
            FileManager.writeToFile(PATH, 10);
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, "Writing to file was unsuccessful.\n");
        }

        List<MoonshineData> drinksList = new ArrayList<>();
        try {
            drinksList = FileManager.readFromFile(PATH);
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, "Reading from file was unsuccessful.\n");
        }

        try {
            System.out.println("Average making time for each ingredient:");
            MoonshineService.getAverageTimeForIngredients(drinksList)
                    .forEach((x, y) -> System.out.println(x.toString() + ':' + y));
        } catch (NullPointerException e) {
            LOGGER.log(Level.SEVERE, "NullPointerException has occurred.\n"
                    + Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        }

        try {
            System.out.println("\nMonth with maximum ingredients: "
                    + MoonshineService.getMaxIngredientsMonth(drinksList) + '\n');
        } catch (NullPointerException e) {
            LOGGER.log(Level.SEVERE, "NullPointerException has occurred.\n"
                    + Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        }

        try {
            System.out.println("Total volume for each drink:");
            MoonshineService.getTotalVolumeForEachDrink(drinksList)
                    .forEach((x, y) -> System.out.println(x + ':' + y));
        } catch (NullPointerException e) {
            LOGGER.log(Level.SEVERE, "NullPointerException has occurred.\n"
                    + Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        }
    }
}