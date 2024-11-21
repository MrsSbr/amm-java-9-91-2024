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

    public static void main(String[] args) {
        List<PlantLogEntry> plants;
        try {
            plants = Reader.read(PATH);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Reading failed.\n" + Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        }

        logger.log(Level.INFO, "Average watering frequency for each flower: " + PlantDiaryService.getAverageWateringFrequency(plants));
        logger.log(Level.INFO, "Plants for each fertilizer: " + PlantDiaryService.findPlantsByFertilizer(plants));
        logger.log(Level.INFO, "Most watered plant: " + PlantDiaryService.findMostWateredPlant(plants));

    }

}