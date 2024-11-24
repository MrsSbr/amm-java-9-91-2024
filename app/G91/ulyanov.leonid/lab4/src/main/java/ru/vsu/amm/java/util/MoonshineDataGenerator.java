package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.MoonshineData;
import ru.vsu.amm.java.enums.DrinkName;
import ru.vsu.amm.java.enums.Ingredient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MoonshineDataGenerator {
    private static final Ingredient[] INGREDIENTS = Ingredient.values();

    private static final DrinkName[] LABELS = DrinkName.values();

    private static final int START_YEAR = 2020;
    private static final float VOLUME_LOWER_BOUND = 0.5f;
    private static final float VOLUME_UPPER_BOUND = 20f;

    private static final float TIME_LOWER_BOUND = 0.5f;
    private static final float TIME_UPPER_BOUND = 40f;

    private static final int MIN_INGREDIENTS = 3;
    private static final int MAX_INGREDIENTS = Ingredient.values().length;

    private float floatRandBetween(float start, float end) {
        Random rand = new Random();
        return start + rand.nextFloat() * (end - start);
    }

    private int intRandBetween(int start, int end) {
        return start + (int) (Math.random() * (end - start));
    }

    private LocalDate generateDate() {
        int year = intRandBetween(START_YEAR, LocalDate.now().getYear());
        int month = intRandBetween(1, 12);
        LocalDate date = LocalDate.of(year, month, 1);
        int day = intRandBetween(1, date.getMonth().length(date.isLeapYear()));
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
        String label = LABELS[intRandBetween(0, LABELS.length - 1)].toString();
        List<Ingredient> ingredients = generateIngredientsList();
        float volume = floatRandBetween(VOLUME_LOWER_BOUND, VOLUME_UPPER_BOUND);
        float time = floatRandBetween(TIME_LOWER_BOUND, TIME_UPPER_BOUND);
        return new MoonshineData(date, label, ingredients, volume, time);
    }

    public List<MoonshineData> generateDrinksList(int size) {
        List<MoonshineData> drinks = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            drinks.add(generateOneMoonshineData());
        }
        return drinks;
    }
}
