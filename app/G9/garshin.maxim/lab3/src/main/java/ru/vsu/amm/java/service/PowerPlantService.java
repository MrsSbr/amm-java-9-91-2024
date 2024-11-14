package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.PowerPlant;
import ru.vsu.amm.java.enums.Type;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PowerPlantService {
    public static Set<Type> getTypesWithPowerOver50LastMonth(List<PowerPlant> powerPlants) {
        if (powerPlants == null || powerPlants.isEmpty()) {
            return Set.of();
        }
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);

        return powerPlants.stream()
                .filter(powerPlant -> powerPlant.date().isAfter(oneMonthAgo) && powerPlant.power() > 50)
                .map(PowerPlant::type)
                .collect(Collectors.toSet());
    }

    public static Map<Type, Double> getAveragePowerByTypeLastThreeMonths(List<PowerPlant> powerPlants) {
        if (powerPlants == null || powerPlants.isEmpty()) {
            return Map.of();
        }
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);

        return powerPlants.stream()
                .filter(powerPlant -> powerPlant.date().isAfter(threeMonthsAgo))
                .collect(Collectors.groupingBy(PowerPlant::type, Collectors.averagingInt(PowerPlant::power)));
    }

    public static int getTotalProductionLastYear(List<PowerPlant> powerPlants) {
        if (powerPlants == null || powerPlants.isEmpty()) {
            return 0;
        }
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);

        return powerPlants.stream()
                .filter(powerPlant -> powerPlant.date().isAfter(oneYearAgo))
                .mapToInt(PowerPlant::power)
                .sum();
    }
}