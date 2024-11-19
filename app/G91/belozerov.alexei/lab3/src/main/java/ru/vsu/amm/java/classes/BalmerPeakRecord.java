package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.enums.AlcoholType;

public record BalmerPeakRecord(AlcoholType type, int amount, boolean hasBalmerPeak) {
}
