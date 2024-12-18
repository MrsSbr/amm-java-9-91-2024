package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.MoonshineData;
import ru.vsu.amm.java.service.MoonshineService;
import ru.vsu.amm.java.util.FileManager;

import java.util.List;

public class MoonshineServiceDemo {
    private static final String PATH = System.getProperty("user.dir") + "\\test.txt";

    private static final int DATA_AMOUNT = 10;

    public static void main(String[] args) {
        FileManager.writeToFile(PATH, DATA_AMOUNT);

        List<MoonshineData> drinksList = FileManager.readFromFile(PATH);

        System.out.println("Average making time for each ingredient:");
        MoonshineService.getAverageTimeForIngredients(drinksList)
                .forEach((x, y) -> System.out.println(x.toString() + ':' + y));

        System.out.println("\nMonth with maximum ingredients: "
                + MoonshineService.getMaxIngredientsMonth(drinksList) + '\n');

        System.out.println("Total volume for each drink:");
        MoonshineService.getTotalVolumeForEachDrink(drinksList)
                .forEach((drink, volume) -> System.out.println(drink.toString() + ':' + volume));
    }
}