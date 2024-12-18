package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class CoffeeGenerator {
    private static final Logger log;

    static {
        log = Logger.getLogger(CoffeeGenerator.class.getName());
    }

    private static final Sort[] SORTS = Sort.values();

    private static final ProcessingType[] PROCESSING_TYPES =
            ProcessingType.values();

    private static final String[] COUNTRIES = {"Brazil", "Columbia", "Vietnam",
            "Ethiopia", "Kenya", "Guatemala", "India"};

    private static final String[] FARMS = {"Farm 1", "Farm 2", "Farm 3",
            "Farm 4", "Farm 5"};

    public static List<Producer> generateProducerList(int count) {
        log.info("Method generateProducerList");
        List<Producer> producerList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            Sort sort = SORTS[random.nextInt(SORTS.length)];
            String country = COUNTRIES[random.nextInt(COUNTRIES.length)];
            String farm = FARMS[random.nextInt(FARMS.length)];
            ProcessingType processingType = PROCESSING_TYPES[random.nextInt
                    (PROCESSING_TYPES.length)];
            int altitude = random.nextInt(700, 2000);
            producerList.add(new Producer(
                    sort, country, farm, processingType, altitude
            ));
        }
        return producerList;
    }
}