package ru.vsu.amm.java;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.enums.DrinkLabel;
import ru.vsu.amm.java.enums.Ingredient;
import ru.vsu.amm.java.service.MoonshineService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoonshineEmptyTests {
    @Test
    public void getMaxIngredientsMonthTestEmpty() {
        int expected = 0;
        int result = MoonshineService.getMaxIngredientsMonth(new ArrayList<>());
        assertEquals(expected, result);
    }

    @Test
    public void getTotalVolumeForEachDrinkTestEmpty() {
        Map<DrinkLabel, Double> expected = new HashMap<>();
        Map<DrinkLabel, Double> result = MoonshineService.getTotalVolumeForEachDrink(new ArrayList<>());
        assertEquals(expected, result);
    }

    @Test
    public void getAverageTimeForIngredientsTestEmpty() {
        Map<Ingredient, Double> expected = new HashMap<>();
        Map<Ingredient, Double> result = MoonshineService.getAverageTimeForIngredients(new ArrayList<>());
        assertEquals(expected, result);
    }
}
