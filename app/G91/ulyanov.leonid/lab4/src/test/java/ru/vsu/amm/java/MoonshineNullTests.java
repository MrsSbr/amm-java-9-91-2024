package ru.vsu.amm.java;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.enums.DrinkLabel;
import ru.vsu.amm.java.enums.Ingredient;
import ru.vsu.amm.java.service.MoonshineService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoonshineNullTests {
    @Test
    public void getMaxIngredientsMonthTestNull() {
        int expected = 0;
        int result = MoonshineService.getMaxIngredientsMonth(null);
        assertEquals(expected, result);
    }


    @Test
    public void getTotalVolumeForEachDrinkTestNull() {
        Map<DrinkLabel, Double> expected = new HashMap<>();
        Map<DrinkLabel, Double> result = MoonshineService.getTotalVolumeForEachDrink(null);
        assertEquals(expected, result);
    }


    @Test
    public void getAverageTimeForIngredientsTestNull() {
        Map<Ingredient, Double> expected = new HashMap<>();
        Map<Ingredient, Double> result = MoonshineService.getAverageTimeForIngredients(null);
        assertEquals(expected, result);
    }
}
