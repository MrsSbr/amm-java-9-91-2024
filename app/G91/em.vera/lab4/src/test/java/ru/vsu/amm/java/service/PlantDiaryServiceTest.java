package ru.vsu.amm.java.service;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.PlantLogEntry;
import ru.vsu.amm.java.enums.Fertilizer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlantDiaryServiceTest {
    private static final List<PlantLogEntry> plants = getPlants();

    @Test
    void testGetAverageWateringFrequencyWithFullList() {
        Map<String, Double> entries = PlantDiaryService.getAverageWateringFrequency(plants);
        assertEquals(2, entries.size());

        assertTrue(entries.containsKey("Rose") && entries.get("Rose").equals(1.));
        assertTrue(entries.containsKey("Tulip") && entries.get("Tulip").equals(0.));
    }

    @Test
    void testGetAverageWateringFrequencyWithEmptyList() {
        Map<String, Double> entries = PlantDiaryService.getAverageWateringFrequency(new ArrayList<>());
        assertEquals(0, entries.size());
    }

    @Test
    void testGetAverageWateringFrequencyWithNull() {
        Map<String, Double> entries = PlantDiaryService.getAverageWateringFrequency(null);
        assertEquals(0, entries.size());
    }

    @Test
    void testFindPlantsByFertilizerWithFullList() {
        Map<Fertilizer, Set<String>> entries = PlantDiaryService.findPlantsByFertilizer(plants);
        assertEquals(2, entries.size());

        assertTrue(entries.get(Fertilizer.GREEN_LEAF).contains("Rose"));
        assertTrue(entries.get(Fertilizer.GREEN_LEAF).contains("Tulip"));
        assertTrue(entries.get(Fertilizer.ROOT_GROWER).contains("Rose"));
    }

    @Test
    void testFindPlantsByFertilizerWithEmptyList() {
        Map<Fertilizer, Set<String>> entries = PlantDiaryService.findPlantsByFertilizer(new ArrayList<>());
        assertEquals(0, entries.size());
    }

    @Test
    void testFindPlantsByFertilizerWithNull() {
        Map<Fertilizer, Set<String>> entries = PlantDiaryService.findPlantsByFertilizer(null);
        assertEquals(0, entries.size());
    }

    @Test
    void testFindMostWateredPlantWithFullList() {
        Optional<String> plant = PlantDiaryService.findMostWateredPlant(plants);
        assertTrue(plant.isPresent());
        assertEquals("Rose", plant.get());
    }

    @Test
    void testFindMostWateredPlantWithEmptyList() {
        Optional<String> plant = PlantDiaryService.findMostWateredPlant(new ArrayList<>());
        assertTrue(plant.isEmpty());
    }

    @Test
    void testFindMostWateredPlantWithNull() {
        Optional<String> plant = PlantDiaryService.findMostWateredPlant(null);
        assertTrue(plant.isEmpty());
    }


    private static List<PlantLogEntry> getPlants() {
        List<PlantLogEntry> plants = new ArrayList<>();
        plants.add(new PlantLogEntry("Rose", 1.5, LocalDate.of(2024, 11, 1), Fertilizer.GREEN_LEAF));
        plants.add(new PlantLogEntry("Tulip", 1.0, LocalDate.of(2024, 11, 1), Fertilizer.GREEN_LEAF));
        plants.add(new PlantLogEntry("Rose", 1.5, LocalDate.of(2024, 11, 2), Fertilizer.ROOT_GROWER));

        return plants;
    }
}