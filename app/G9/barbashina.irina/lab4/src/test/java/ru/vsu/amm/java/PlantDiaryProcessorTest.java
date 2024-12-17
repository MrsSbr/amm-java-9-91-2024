package ru.vsu.amm.java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.vsu.amm.java.fortests.GetSomePlantRecords.getSomePlantRecords;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.PlantRecord;
import ru.vsu.amm.java.enums.FertilizerBrand;
import ru.vsu.amm.java.enums.Plant;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

public class PlantDiaryProcessorTest {
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
    public void testListContents() {
        assertFalse(records.isEmpty());
        assertEquals(3, records.size());
    }

    @Test
    public void testCheckFirstRecord() {
        PlantRecord firstRecord = records.get(0);
        assertEquals(LocalDate.of(2023,10,15), firstRecord.getDate());
        assertEquals(Plant.ROSEMARY, firstRecord.getPlantName());
        assertEquals(100, firstRecord.getWaterAmount());
        assertEquals(FertilizerBrand.ATAMI, firstRecord.getFertilizerBrand());
    }

    @Test
    public void testCheckLastRecord() {
        PlantRecord lastRecord = records.get(records.size() - 1);
        assertEquals(LocalDate.of(2000,11,11), lastRecord.getDate());
        assertEquals(Plant.CACTUS, lastRecord.getPlantName());
        assertEquals(1000, lastRecord.getWaterAmount());
        assertEquals(FertilizerBrand.ATAMI, lastRecord.getFertilizerBrand());
    }
}
