package ru.vsu.amm.java;

import ru.vsu.amm.java.Service.Service;
import ru.vsu.amm.java.FileService.FileReader;
import ru.vsu.amm.java.Model.FileEntity;

import java.util.List;
import java.util.logging.Logger;

public class Main {

    private static final String PATH = "app/G91/kireev.dmitrii/lab4/src/main/java/ru/vsu/amm/java/TaskInquisitions.txt";

    private static final String CONFESSION_COUNT_PER_INSTRUMENT = "How many times was confession made by each instrument? ";
    private static final String SUMMARY_DURATION = "Summary duration for every suspect ";
    private static final String NO_CONFESSION = "No confession per every instrument for one user ";

    public static void main(String[] args) {

        FileReader reader = new FileReader();
        Service service = new Service();
            List<FileEntity> entities = reader.read(PATH);

            System.out.println(CONFESSION_COUNT_PER_INSTRUMENT + service.getConfessionCountPerInstrument(entities).toString());
            System.out.println(SUMMARY_DURATION + service.getTortureDurationPerSuspect(entities));
            System.out.println(NO_CONFESSION + service.getTorturedByEveryInstrumentWithoutConfession(entities));

    }
}
