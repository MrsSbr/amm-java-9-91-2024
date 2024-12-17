package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.PlantRecord;
import ru.vsu.amm.java.service.PlantDiaryAnalysis;
import ru.vsu.amm.java.util.PlantDiaryProcessor;

import java.util.List;

public class PlantApplication {
    private static final String FILE_PATH = "app/G9/barbashina.irina/lab4/plant_diary.txt";

    public static void main(String[] args) {
        List<PlantRecord> records = PlantDiaryProcessor.readPlantRecords(FILE_PATH);
        for (PlantRecord record : records) {
            System.out.println(record);
        }
        System.out.println("Average watering: " + PlantDiaryAnalysis.calculateAverageWatering(records));
        System.out.println("Fertilizers and plants: " + PlantDiaryAnalysis.findFertilizedPlants(records));
        System.out.println("A plant with maximum watering: " + PlantDiaryAnalysis.findPlantWithMostWater(records));
    }
}