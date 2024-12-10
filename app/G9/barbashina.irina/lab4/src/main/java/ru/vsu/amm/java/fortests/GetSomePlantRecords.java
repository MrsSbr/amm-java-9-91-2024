package ru.vsu.amm.java.fortests;

import ru.vsu.amm.java.entity.PlantRecord;
import ru.vsu.amm.java.enums.FertilizerBrand;
import ru.vsu.amm.java.enums.Plant;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class GetSomePlantRecords {
    public static List<PlantRecord> getSomePlantRecords() {
        return Arrays.asList(
                new PlantRecord(LocalDate.of(2023, 10, 15), Plant.ROSEMARY, 100, FertilizerBrand.ATAMI),
                new PlantRecord(LocalDate.of(2023, 10, 18), Plant.ROSEMARY, 120, FertilizerBrand.RASTEA),
                new PlantRecord(LocalDate.of(2023, 10, 20), Plant.FICUS, 150, FertilizerBrand.HESI)
        );
    }
}
