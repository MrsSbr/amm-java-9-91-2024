package ru.vsu.amm.java;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.PlantRecord;
import ru.vsu.amm.java.enums.FertilizerBrand;
import ru.vsu.amm.java.enums.Plant;
import ru.vsu.amm.java.service.PlantDiaryAnalysis;



import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static ru.vsu.amm.java.fortests.GetSomePlantRecords.getSomePlantRecords;

public class PlantDiaryAnalysisTest {
    private List<PlantRecord> records;

    @BeforeEach
    public void setup() {
        records = getSomePlantRecords();
    }

    @AfterEach
    public void free(){
        records = null;
    }

    @Test
    public void testCalculateAverageWatering() {
        Map<Plant, Double> averageWatering = PlantDiaryAnalysis.calculateAverageWatering(records);
        assertEquals(110, averageWatering.get(Plant.ROSEMARY));
        assertEquals(200, averageWatering.get(Plant.CACTUS));
    }

    @Test
    public void testFindFertilizedPlants_ContainsBrands() {
        Map<FertilizerBrand, List<Plant>> fertilizedPlants = PlantDiaryAnalysis.findFertilizedPlants(records);
        assertTrue(fertilizedPlants.containsKey(FertilizerBrand.ATAMI));
        assertFalse(fertilizedPlants.containsKey(FertilizerBrand.PLAGRON));
    }

    @Test
    public void testFindFertilizedPlants_AtamiCount() {
        Map<FertilizerBrand, List<Plant>> fertilizedPlants = PlantDiaryAnalysis.findFertilizedPlants(records);
        assertEquals(1, fertilizedPlants.get(FertilizerBrand.ATAMI).size());
        assertNotEquals(null, fertilizedPlants.get(FertilizerBrand.HESI));
    }

    @Test
    public void testFindPlantWithMostWater() {
        Optional<Plant> plantWithMostWater = PlantDiaryAnalysis.findPlantWithMostWater(records);
        assertTrue(plantWithMostWater.isPresent());
        assertEquals(Plant.ROSEMARY, plantWithMostWater.get());
    }
}
