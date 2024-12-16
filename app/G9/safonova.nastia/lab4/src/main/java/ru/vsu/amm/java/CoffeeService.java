package ru.vsu.amm.java;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CoffeeService {

    public static Map<ProcessingType, List<Sort>> getSortsByProcessingType
            (List<Producer> producers){
        return producers.stream()
                .filter(producer -> producer.processingType() != null && producer.sort() != null)
                .collect(Collectors.groupingBy(
                        Producer::processingType,
                        Collectors.mapping(Producer::sort,
                                Collectors.collectingAndThen(
                                        Collectors.toSet(),
                                        List::copyOf
                                )
                        )
                ));
    }

    public static Set<String> getCountriesWithHighAltitude(List<Producer> producers){
        return producers.stream()
                .filter(info -> info.altitude() > 1500)
                .map(Producer::country)
                .collect(Collectors.toSet());
    }

    public static Map<String, Long> getCoffeeCountByFarm(List<Producer> producers) {
        return producers.stream()
                .collect(Collectors.groupingBy(
                        Producer::farm,
                        Collectors.counting()
                ));
    }
}
