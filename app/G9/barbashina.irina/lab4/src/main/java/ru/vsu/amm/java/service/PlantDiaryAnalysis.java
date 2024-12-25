package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.PlantRecord;
import ru.vsu.amm.java.enums.FertilizerBrand;
import ru.vsu.amm.java.enums.Plant;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlantDiaryAnalysis {
    public static Map<Plant, Double> calculateAverageWatering(List<PlantRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(PlantRecord::getPlantName,
                        Collectors.averagingDouble(PlantRecord::getWaterAmount)));
    }

    public static Map<FertilizerBrand, List<Plant>> findFertilizedPlants(List<PlantRecord> records) {
        return records.stream()
                .filter(x -> x.getFertilizerBrand() != null)
                .collect(Collectors.groupingBy(PlantRecord::getFertilizerBrand,
                        Collectors.mapping(PlantRecord::getPlantName, Collectors.toList())));
    }

    public static Optional<Plant> findPlantWithMostWater(List<PlantRecord> records) {
        Map<Plant, Integer> waterAmountByPlant = records.stream()
                .collect(Collectors.groupingBy(PlantRecord::getPlantName,
                        Collectors.summingInt(PlantRecord::getWaterAmount)));
        return waterAmountByPlant.entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey);
    }
}
