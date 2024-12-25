package ru.vsu.amm.java.model;

import ru.vsu.amm.java.enums.Nationality;
import ru.vsu.amm.java.enums.ShipType;

import java.time.LocalDate;

public record Ship(
        LocalDate date,
        ShipType type,
        Nationality nationality,
        long goldNumber,
        long rumBarrelsNumber,
        boolean wasBoarding
) {
}
