package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.MoonshineRecord;
import ru.vsu.amm.java.enums.Ingredient;


import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MoonshineService {
    public static Map<Ingredient, Double> getAverageTimeForIngredients(List<MoonshineRecord> drinksList)
            throws NullPointerException {
        if (drinksList == null) {
            throw new NullPointerException();
        }

        HashMap<Ingredient, List<Float>> ingredientsTime = new HashMap<>();

        Arrays.stream(Ingredient.values())
                .forEach(ingredient -> ingredientsTime.put(ingredient, new ArrayList<>()));

        drinksList
                .forEach(x -> x.ingredients()
                        .forEach(ingredient -> ingredientsTime.get(ingredient).add(x.makingTime())));

        return ingredientsTime.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        x -> x.getValue()
                                .stream()
                                .mapToDouble(Float::doubleValue)
                                .average()
                                .orElse(0.0)));
    }

    public static int getMaxIngredientsMonth(List<MoonshineRecord> drinksList)
            throws NullPointerException {
        if (drinksList == null) {
            throw new NullPointerException();
        }

        HashMap<Integer, List<Ingredient>> monthsIngredients = new HashMap<>();

        drinksList.forEach(x -> {
            int month = x.date().getMonthValue();
            if (!monthsIngredients.containsKey(month)) {
                monthsIngredients.put(month, new ArrayList<>());
            } else {
                monthsIngredients.put(month,
                        Stream.concat(monthsIngredients.get(month).stream(),
                                x.ingredients().stream()).toList());
            }
        });

        return monthsIngredients.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, x -> (long) x.getValue().size()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .orElse(Map.entry(-1, -1L))
                .getKey();
    }

    public static Map<String, Float> getTotalVolumeForEachDrink(List<MoonshineRecord> drinksList)
            throws NullPointerException {
        if (drinksList == null) {
            throw new NullPointerException();
        }
        HashMap<String, Float> drinksMap = new HashMap<>();
        drinksList.forEach(x -> {
            if (!drinksMap.containsKey(x.label())) {
                drinksMap.put(x.label(), x.volume());
            } else {
                drinksMap.put(x.label(), drinksMap.get(x.label()) + x.volume());
            }
        });
        return drinksMap;
    }
}
