package ru.vsu.amm.java.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entites.PlantLog;
import ru.vsu.amm.java.enums.PlantType;
import ru.vsu.amm.java.services.PlantLogService;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PlantLogServiceTest {

    private PlantLogService plantLogService;
    private List<PlantLog> testPlantLogs;

    // создание списков и экземпляров
    @BeforeEach
    void setUp() {
        testPlantLogs = new ArrayList<>(Arrays.asList(
                new PlantLog("Rose", PlantType.FLOWERING, 15, 200, LocalDate.of(2024, 7, 20)),
                new PlantLog("Tomato", PlantType.VEGETABLE, 10, 50, LocalDate.of(2024, 7, 15)),
                new PlantLog("Basil", PlantType.HERB, 8, 70, LocalDate.of(2024, 7, 25)),
                new PlantLog("Apple", PlantType.FRUIT, 20, 300, LocalDate.of(2024, 7, 10)),
                new PlantLog("Orchid", PlantType.FLOWERING, 12, 250, LocalDate.of(2024, 7, 18)),
                new PlantLog("Strawberry", PlantType.FRUIT, 10, 80, LocalDate.of(2024, 7, 2)),
                new PlantLog("Rose", PlantType.FLOWERING, 12, 100, LocalDate.of(2024, 8, 20)),
                new PlantLog("Mint", PlantType.HERB, 6, 60, LocalDate.of(2024, 7, 1)),
                new PlantLog("Carrot", PlantType.VEGETABLE, 10, 40, LocalDate.of(2024, 6, 25))
        ));
        plantLogService = new PlantLogService(testPlantLogs);
    }

    // июль
    @Test
    void findMonthWithMostFlowers_ReturnsCorrectMonth() {
        Optional<Month> month = PlantLogService.findMonthWithMostFlowers();
        assertTrue(month.isPresent());
        assertEquals(Month.JULY, month.get());
    }

    // если пусто, то empty
    @Test
    void findMonthWithMostFlowers_NoFlowers_ReturnsEmpty() {
        PlantLogService service = new PlantLogService(
                testPlantLogs.stream().filter(x -> x.type() != PlantType.FLOWERING).toList());
        Optional<Month> month = PlantLogService.findMonthWithMostFlowers();
        assertTrue(month.isEmpty());
    }

    //на данном наборе carrot
    @Test
    void findPlantsWithLowestEarnings_ReturnsCorrectPlants() {
        Set<String> plants = plantLogService.findPlantsWithLowestEarnings();
        assertEquals(Set.of("Carrot"), plants);
    }

    // если единтсвенное в списке
    @Test
    void findPlantsWithLowestEarnings_SinglePlant_ReturnsThatPlant() {
        List<PlantLog> logs = new ArrayList<>(List.of(new PlantLog("Rose", PlantType.FLOWERING, 15, 100, LocalDate.of(2024, 7, 20))));
        plantLogService = new PlantLogService(logs);
        Set<String> plants = plantLogService.findPlantsWithLowestEarnings();
        assertEquals(Set.of("Rose"), plants);
    }

    // если одинаковый  мнимальный доход
    @Test
    void findPlantsWithLowestEarnings_SameEarningsForAllPlants_ReturnsAllPlants() {
        List<PlantLog> logs = new ArrayList<>(Arrays.asList(
                new PlantLog("Rose", PlantType.FLOWERING, 15, 100, LocalDate.of(2024, 7, 20)),
                new PlantLog("Tomato", PlantType.VEGETABLE, 10, 100, LocalDate.of(2024, 7, 15)),
                new PlantLog("Basil", PlantType.HERB, 8, 100, LocalDate.of(2024, 7, 25))
        ));
        plantLogService = new PlantLogService(logs);
        Set<String> plants = plantLogService.findPlantsWithLowestEarnings();
        assertEquals(Set.of("Rose", "Tomato", "Basil"), plants);
    }

    // ситуация, когда у одного растения много размеров горшков
    @Test
    void findPotSizesPerPlant_SinglePlantMultiplePots() {
        List<PlantLog> logs = new ArrayList<>(Arrays.asList(
                new PlantLog("Rose", PlantType.FLOWERING, 15, 200, LocalDate.of(2024, 7, 20)),
                new PlantLog("Rose", PlantType.FLOWERING, 10, 200, LocalDate.of(2024, 7, 20)),
                new PlantLog("Rose", PlantType.FLOWERING, 12, 200, LocalDate.of(2024, 7, 20))
        ));
        plantLogService = new PlantLogService(logs);

        Map<String, Set<Integer>> potSizes = plantLogService.findPotSizesPerPlant();
        assertEquals(1, potSizes.size());
        assertEquals(Set.of(15, 10, 12), potSizes.get("Rose"));
    }

}
