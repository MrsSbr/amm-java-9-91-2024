package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.DrinkLabel;
import ru.vsu.amm.java.enums.Ingredient;

import java.time.LocalDate;
import java.util.List;

public record MoonshineData(LocalDate date,
                            DrinkLabel label,
                            List<Ingredient> ingredients,
                            double volume,
                            double makingTime) {
}
