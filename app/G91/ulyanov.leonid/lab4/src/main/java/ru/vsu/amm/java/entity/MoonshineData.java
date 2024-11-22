package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.Ingredient;

import java.time.LocalDate;
import java.util.List;

public record MoonshineData(LocalDate date, String label, List<Ingredient> ingredients,
                            float volume, float makingTime) {
}
