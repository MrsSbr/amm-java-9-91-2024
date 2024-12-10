package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.PlantRecord;
import ru.vsu.amm.java.service.PlantDiaryAnalysis;
import ru.vsu.amm.java.util.PlantDiaryProcessor;

import java.util.List;

public class PlantApplication {
    public static void main(String[] args) {
        String filePath = "app/G9/barbashina.irina/lab4/plant_diary.txt";
        List<PlantRecord> records = PlantDiaryProcessor.readPlantRecords(filePath);
        for (PlantRecord record : records) {
            System.out.println(record);
        }
        System.out.println("Average watering: " + PlantDiaryAnalysis.calculateAverageWatering(records));
        System.out.println("Fertilizers and plants: " + PlantDiaryAnalysis.findFertilizedPlants(records));
        System.out.println("A plant with maximum watering: " + PlantDiaryAnalysis.findPlantWithMostWater(records));
    }
}