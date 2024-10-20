package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.RobbedShip;
import ru.vsu.amm.java.enums.Nationality;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class RobbedShipService {

    public Map<Nationality, Long> boardedShipsByNationality(List<RobbedShip> ships) {
        return ships.stream()
                .filter(RobbedShip::isBoarding)
                .collect(Collectors.groupingBy(RobbedShip::getNationality, Collectors.counting()));
    }

    public Optional<Map.Entry<LocalDate, Integer>> leastProfitableMonth(List<RobbedShip> ships) {
        return ships.stream()
                .collect(Collectors.groupingBy(ship -> ship.getRobbedDate().withDayOfMonth(1),
                        Collectors.summingInt(RobbedShip::getGoldCount)))
                .entrySet().stream()
                .min(Map.Entry.comparingByValue());
    }

    public List<RobbedShip> topRumShips(List<RobbedShip> ships) {
        LocalDate threeYearsAgo = LocalDate.now().minusYears(3);
        return ships.stream()
                .filter(ship -> ship.getRobbedDate().isAfter(threeYearsAgo))
                .sorted(Comparator.comparingInt(RobbedShip::getBarrelCount).reversed())
                .collect(Collectors.toList());
    }
}
