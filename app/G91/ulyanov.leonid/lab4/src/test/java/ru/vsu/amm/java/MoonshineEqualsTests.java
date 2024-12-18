package ru.vsu.amm.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.MoonshineData;
import ru.vsu.amm.java.enums.DrinkLabel;
import ru.vsu.amm.java.enums.Ingredient;
import ru.vsu.amm.java.service.MoonshineService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoonshineEqualsTests {
    private List<MoonshineData> drinks;

    @BeforeEach
    public void start() {
        drinks = generateMoonshineData();
    }

    @Test
    public void getMaxIngredientsMonthTest() {
        int expected = 7;
        int result = MoonshineService.getMaxIngredientsMonth(drinks);
        assertEquals(expected, result);
    }

    @Test
    public void getTotalVolumeForEachDrinkTest() {
        Map<DrinkLabel, Double> result = MoonshineService.getTotalVolumeForEachDrink(drinks);
        Map<DrinkLabel,Double> expected = new HashMap<>() {{
            put(DrinkLabel.POTION, 4.0);
            put(DrinkLabel.TINCTURE, 12.0);
            put(DrinkLabel.SCOTCH, 20.0);
            put(DrinkLabel.WHISKEY, 18.0);
        }};
        assertEquals(expected, result);
    }

    @Test
    public void getAverageTimeForIngredientsTest() {
        Map<Ingredient, Double> result = MoonshineService.getAverageTimeForIngredients(drinks);
        Map<Ingredient,Double> expected = new HashMap<>() {{
            put(Ingredient.MALT, 13.0);
            put(Ingredient.SUGAR, 13.0);
            put(Ingredient.FRUIT, 16.0);
            put(Ingredient.YEAST, 14.0);
            put(Ingredient.BERRIES, 13.285714285714286);
            put(Ingredient.WATER, 14.4);
        }};
        assertEquals(expected, result);
    }

    public static List<MoonshineData> generateMoonshineData() {
        return Arrays.asList(
                new MoonshineData(LocalDate.of(2024, 10, 15), DrinkLabel.WHISKEY,
                        Arrays.asList(Ingredient.WATER, Ingredient.FRUIT, Ingredient.BERRIES),
                        10, 30),
                new MoonshineData(LocalDate.of(2024, 8, 15), DrinkLabel.SCOTCH,
                        Arrays.asList(Ingredient.MALT, Ingredient.YEAST, Ingredient.BERRIES),
                        2, 12),
                new MoonshineData(LocalDate.of(2024, 7, 15), DrinkLabel.TINCTURE,
                        Arrays.asList(Ingredient.WATER, Ingredient.SUGAR, Ingredient.BERRIES),
                        6, 12),
                new MoonshineData(LocalDate.of(2024, 6, 15), DrinkLabel.POTION,
                        Arrays.asList(Ingredient.WATER, Ingredient.SUGAR, Ingredient.YEAST),
                        4, 18),
                new MoonshineData(LocalDate.of(2024, 6, 15), DrinkLabel.WHISKEY,
                        Arrays.asList(Ingredient.MALT, Ingredient.FRUIT, Ingredient.BERRIES),
                        8, 15),
                new MoonshineData(LocalDate.of(2024, 7, 15), DrinkLabel.TINCTURE,
                        Arrays.asList(Ingredient.YEAST, Ingredient.MALT, Ingredient.BERRIES),
                        6, 12),
                new MoonshineData(LocalDate.of(2024, 7, 15), DrinkLabel.SCOTCH,
                        Arrays.asList(Ingredient.SUGAR, Ingredient.WATER, Ingredient.BERRIES),
                        10, 9),
                new MoonshineData(LocalDate.of(2024, 5, 15), DrinkLabel.SCOTCH,
                        Arrays.asList(Ingredient.WATER, Ingredient.FRUIT, Ingredient.BERRIES),
                        8, 3)
        );
    }
}
