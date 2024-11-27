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
    private static final Logger logger = Logger.getLogger(MoonshineServiceDemo.class.getName());
    private static final String PATH = System.getProperty("user.dir") + "\\test.txt";

    private static final int DATA_AMOUNT = 10;

    private static final String NULL_POINTER_MSG = "NullPointerException has occurred.\n";
    private static final String WRITE_FAIL_MSG = "Writing to file was unsuccessful.\n";
    private static final String READ_FAIL_MSG = "Reading from file was unsuccessful.\n";

    public static void main(String[] args) {
        try {
            FileManager.writeToFile(PATH, DATA_AMOUNT);
        } catch (RuntimeException e) {
            logger.log(Level.SEVERE, WRITE_FAIL_MSG, e);
        }

        List<MoonshineData> drinksList = new ArrayList<>();
        try {
            drinksList = FileManager.readFromFile(PATH);
        } catch (RuntimeException e) {
            logger.log(Level.SEVERE, READ_FAIL_MSG, e);
        }

        try {
            System.out.println("Average making time for each ingredient:");
            MoonshineService.getAverageTimeForIngredients(drinksList)
                    .forEach((x, y) -> System.out.println(x.toString() + ':' + y));
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, NULL_POINTER_MSG
                    + Arrays.toString(e.getStackTrace()));
        }

        try {
            System.out.println("\nMonth with maximum ingredients: "
                    + MoonshineService.getMaxIngredientsMonth(drinksList) + '\n');
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, NULL_POINTER_MSG
                    + Arrays.toString(e.getStackTrace()));
        }

        try {
            System.out.println("Total volume for each drink:");
            MoonshineService.getTotalVolumeForEachDrink(drinksList)
                    .forEach((drink, volume) -> System.out.println(drink.toString() + ':' + volume));
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, NULL_POINTER_MSG
                    + Arrays.toString(e.getStackTrace()));
        }
    }
}