package ru.vsu.amm.java.service;

import ru.vsu.amm.java.enums.Nationality;
import ru.vsu.amm.java.model.Ship;

import java.time.LocalDate;
import java.time.Month;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShipService {

    public static Map<Nationality, Long> boundingShipCountByNationality(List<Ship> ships) {
        if (ships == null || ships.isEmpty()) {
            return new HashMap<>();
        }
        return ships.stream()
                .filter(Ship::wasBoarding)
                .collect(Collectors.groupingBy(Ship::nationality, Collectors.counting()));
    }

    public static Month lessProfitMonth(List<Ship> ships) {
        if (ships == null || ships.isEmpty()) {
            return Month.JANUARY;
        }
        return ships.stream()
                .collect(Collectors.groupingBy(ship -> ship.date().getMonth(),
                        Collectors.summingLong(Ship::goldNumber)))
                .entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .orElseGet(() -> new AbstractMap.SimpleEntry<>(Month.JANUARY, 0L))
                .getKey();
    }

    public static List<Ship> mostRumsStocks(List<Ship> ships) {
        if (ships == null || ships.isEmpty()) {
            return new ArrayList<>();
        }
        LocalDate todayThreeYearsAgo = LocalDate.now().minusYears(3);
        return ships.stream()
                .filter(ship -> ship.date().isAfter(todayThreeYearsAgo))
                .sorted(Comparator.comparingLong(Ship::rumBarrelsNumber).reversed())
                .toList();
    }
}
