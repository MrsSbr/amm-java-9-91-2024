package ru.vsu.amm.java.battlerecord;

import ru.vsu.amm.java.enums.Animal;

import java.time.LocalDate;

public record BattleRecord(LocalDate date, String name, String ludus,
                           Animal animal, boolean gladiatorWon, boolean gladiatorPardoned) {
}