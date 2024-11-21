package ru.vsu.amm.java;

import ru.vsu.amm.java.ApiService.ApiService;
import ru.vsu.amm.java.FileService.FileReader;
import ru.vsu.amm.java.Model.FileEntity;

import java.util.List;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final String PATH = "app/G91/kireev.dmitrii/lab4/src/main/java/ru/vsu/amm/java/TaskInquisitions.txt";

    public static void main(String[] args) {

        FileReader reader = new FileReader();
        ApiService apiService = new ApiService();
        List<FileEntity> entities = reader.read(PATH);


       logger.info("How many times was electricity generated by each instrument? " + apiService.getConfessionCountPerInstrument(entities).toString());
       logger.info("summary duration for every suspect " + apiService.getTortureDurationPerSuspect(entities));
       logger.info("no confession per every instrument for one user " + apiService.getTorturedByEveryInstrumentWithoutConfession(entities));

    }
}
