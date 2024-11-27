package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.MoonshineData;
import ru.vsu.amm.java.enums.DrinkLabel;
import ru.vsu.amm.java.enums.Ingredient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MoonshineDataGenerator {
    private static final Ingredient[] INGREDIENTS = Ingredient.values();

    private static final DrinkLabel[] LABELS = DrinkLabel.values();

    private static final int START_YEAR = 2020;
    private static final double VOLUME_LOWER_BOUND = 0.5;
    private static final double VOLUME_UPPER_BOUND = 20.0;

    private static final double TIME_LOWER_BOUND = 0.5;
    private static final double TIME_UPPER_BOUND = 40.0;

    private static final int MIN_INGREDIENTS = 3;
    private static final int MAX_INGREDIENTS = Ingredient.values().length;

    private double doubleRandBetween(double start, double end) {
        Random rand = new Random();
        return start + rand.nextDouble() * (end - start);
    }

    private int intRandBetween(int start, int end) {
        Random rand = new Random();
        return start + rand.nextInt() * (end - start);
    }

    private LocalDate generateDate() {
        int currentYear = LocalDate.now().getYear();
        int year = intRandBetween(START_YEAR, currentYear);
        int month = intRandBetween(1, 12);
        LocalDate date = LocalDate.of(year, month, 1);
        int maxDayOfMonth = date.getMonth().length(date.isLeapYear());
        int day = intRandBetween(1, maxDayOfMonth);
        return LocalDate.of(year, month, day);
    }

    private List<Ingredient> generateIngredientsList() {
        int randSize = intRandBetween(MIN_INGREDIENTS, MAX_INGREDIENTS);
        List<Ingredient> ingredients = new ArrayList<>();
        List<Ingredient> unusedIngredients = new ArrayList<>(Arrays.stream(INGREDIENTS.clone()).toList());
        for (int i = 0; i < randSize; i++) {
            int currentSize = unusedIngredients.size();
            ingredients.add(unusedIngredients.remove(intRandBetween(0, currentSize - 1)));
        }
        return ingredients;
    }

    public MoonshineData generateOneMoonshineData() {
        LocalDate date = generateDate();
        DrinkLabel label = LABELS[intRandBetween(0, LABELS.length - 1)];
        List<Ingredient> ingredients = generateIngredientsList();
        double volume = doubleRandBetween(VOLUME_LOWER_BOUND, VOLUME_UPPER_BOUND);
        double time = doubleRandBetween(TIME_LOWER_BOUND, TIME_UPPER_BOUND);
        return new MoonshineData(date, label, ingredients, volume, time);
    }
}
