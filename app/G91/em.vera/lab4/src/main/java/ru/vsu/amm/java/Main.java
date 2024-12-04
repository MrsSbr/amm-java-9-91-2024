package ru.vsu.amm.java;


import ru.vsu.amm.java.entity.PlantLogEntry;
import ru.vsu.amm.java.service.PlantDiaryService;
import ru.vsu.amm.java.utils.Reader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final String PATH = "app/G91/em.vera/lab4/src/main/java/ru/vsu/amm/java/resources/plants";

    public static void main(String[] args) throws IOException {
        try {
            List<PlantLogEntry> plants = Reader.read(PATH);
            System.out.println("Average watering frequency for each flower: "
                    + PlantDiaryService.getAverageWateringFrequency(plants));
            System.out.println("Plants for each fertilizer: "
                    + PlantDiaryService.findPlantsByFertilizer(plants));
            System.out.println("Most watered plant: "
                    + PlantDiaryService.findMostWateredPlant(plants));
        } catch (IOException e) {
            logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            System.out.println("Reading was failed: " + PATH + "\n");
        }
    }

}