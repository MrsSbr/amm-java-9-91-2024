package ru.vsu.amm.java.main.OlympicRecord;

import ru.vsu.amm.java.main.Enum.KindOfSport;

public record OlympicMedalsRecord(String country, KindOfSport sport,
                                  String athlete, Integer place) {
};
