package ru.vsu.amm.java;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.service.MoonshineService;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoonshineNullAndEmptyTests {
    @Test
    public void getMaxIngredientsMonthTestNull() {
        assertEquals(0, MoonshineService.getMaxIngredientsMonth(null));
    }

    @Test
    public void getMaxIngredientsMonthTestEmpty() {
        assertEquals(0, MoonshineService.getMaxIngredientsMonth(new ArrayList<>()));
    }

    @Test
    public void getTotalVolumeForEachDrinkTestNull() {
        assertEquals(new HashMap<>(), MoonshineService.getTotalVolumeForEachDrink(null));
    }

    @Test
    public void getTotalVolumeForEachDrinkTestEmpty() {
        assertEquals(new HashMap<>(), MoonshineService.getTotalVolumeForEachDrink(new ArrayList<>()));
    }

    @Test
    public void getAverageTimeForIngredientsTestNull() {
        assertEquals(new HashMap<>(), MoonshineService.getAverageTimeForIngredients(null));
    }

    @Test
    public void getAverageTimeForIngredientsTestEmpty() {
        assertEquals(new HashMap<>(), MoonshineService.getAverageTimeForIngredients(new ArrayList<>()));
    }
}
