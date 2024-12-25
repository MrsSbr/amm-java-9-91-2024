package ru.vsu.amm.java;

import ru.vsu.amm.java.entites.PlantLog;
import ru.vsu.amm.java.readers.PlantLogReader;
import ru.vsu.amm.java.services.PlantLogService;

import java.io.IOException;
import java.time.Month;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        String filePath = "app/G91/abdrahmanova.evgeniya/lab4/src/main/java/ru/vsu/amm/java/files/plants";
        try {
            List<PlantLog> logs = PlantLogReader.readPlantLogsFromFile(filePath);
            logs.forEach(System.out::println);

            PlantLogService plantLogService = new PlantLogService(logs);
            Optional<Month> month = plantLogService.findMonthWithMostFlowers();

            System.out.println("Month with most flowers: " + month.orElse(null));

            System.out.println("Pot sizes per plant: " + plantLogService.findPotSizesPerPlant());

            System.out.println("Plants with lowest earnings: " + plantLogService.findPlantsWithLowestEarnings());

        } catch (IOException e) {
            System.err.println("failed: " + e.getMessage());
        }
    }
}
