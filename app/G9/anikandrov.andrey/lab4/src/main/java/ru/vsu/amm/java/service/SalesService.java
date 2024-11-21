package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.Sale;
import ru.vsu.amm.java.enums.Showroom;
import ru.vsu.amm.java.enums.Car;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SalesService {

    public SalesService() {
    }

    public static Map<Car, Showroom> findMaxMarkup(List<Sale> sales) {
        return sales.stream()
                .collect(Collectors.groupingBy(Sale::getCar))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            return entry.getValue().stream()
                                    .max(Comparator.comparing(Sale::getMarkup))
                                    .map(Sale::getDealCenter)
                                    .orElse(null);
                        }
                ));
    }

    public static Showroom findBestShowroom(List<Sale> sales) {
        LocalDate threeYearsAgo = LocalDate.now().minusYears(3);

        Map<Showroom, Set<String>> UniquePairs =
                sales.stream()
                        .filter(sale -> sale.getDateOfSale().isAfter(threeYearsAgo))
                        .collect(Collectors.groupingBy(
                                Sale::getDealCenter,
                                Collectors.mapping(sale -> sale.getCar().toString() + '_' + sale.getEquipment(), Collectors.toSet())
                        ));

        return UniquePairs.entrySet().stream()
                .max(Comparator.comparingInt(entry ->
                        entry.getValue().size()))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public static Map<Showroom, Integer> findTotalMarkup(List<Sale> sales) {
        return sales.stream()
                .collect(Collectors.groupingBy(
                        Sale::getDealCenter,
                        Collectors.summingInt(Sale::getMarkup)
                ));
    }

}
