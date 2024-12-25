package ru.vsu.amm.java;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CoffeeService {
    private static final Logger log;
    private static final int HIGHT = 1500;

    static {
        log = Logger.getLogger(CoffeeService.class.getName());
    }

    public static Map<ProcessingType, Set<Sort>> getSortsByProcessingType
            (List<Producer> producers) {
        log.info("Method getSortsByProcessingType");
        return producers.stream()
                .filter(producer -> producer.processingType() != null && producer.sort() != null)
                .collect(Collectors.groupingBy(
                        Producer::processingType,
                        Collectors.mapping(
                                Producer::sort,
                                Collectors.toSet()
                        )
                ));
    }

    public static Set<String> getCountriesWithHighAltitude(List<Producer> producers) {
        log.info("Method getCountriesWithHighAltitude");
        return producers.stream()
                .filter(info -> info.altitude() > HIGHT)
                .map(Producer::country)
                .collect(Collectors.toSet());
    }

    public static Map<String, Long> getCoffeeCountByFarm(List<Producer> producers) {
        log.info("Method getCoffeeCountByFarm");
        return producers.stream()
                .collect(Collectors.groupingBy(
                        Producer::farm,
                        Collectors.counting()
                ));
    }
}