package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.MoonshineData;
import ru.vsu.amm.java.enums.DrinkLabel;
import ru.vsu.amm.java.enums.Ingredient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MoonshineService {
    public static Map<Ingredient, Double> getAverageTimeForIngredients(List<MoonshineData> drinksList) {
        if (drinksList == null) {
            return new HashMap<>();
        }

        return drinksList.stream()
                .flatMap(drinkData -> drinkData.ingredients().stream()
                        .map(x -> Map.entry(x, drinkData.makingTime())))
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.averagingDouble(Map.Entry::getValue)));
    }

    public static int getMaxIngredientsMonth(List<MoonshineData> drinksList) {
        if (drinksList == null) {
            return 0;
        }

        return drinksList.stream()
                .collect(Collectors.groupingBy(x -> x.date().getMonthValue(),
                        Collectors.summingInt(x -> x.ingredients().size())))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(0);
    }

    public static Map<DrinkLabel, Double> getTotalVolumeForEachDrink(List<MoonshineData> drinksList) {
        if (drinksList == null) {
            return new HashMap<>();
        }

        return drinksList.stream()
                .collect(Collectors.groupingBy(MoonshineData::label,
                        Collectors.summingDouble(MoonshineData::volume)));
    }
}
