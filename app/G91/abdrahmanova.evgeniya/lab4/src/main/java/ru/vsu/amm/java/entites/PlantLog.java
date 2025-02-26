package ru.vsu.amm.java.entites;

import ru.vsu.amm.java.enums.PlantType;

import java.time.LocalDate;

public record PlantLog(
        String Name,
        PlantType type,
        int sizePot,
        int price,
        LocalDate date
) {
}
