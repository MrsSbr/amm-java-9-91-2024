package ru.vsu.amm.java;

import java.util.List;

public class CoffeeApplication {

    public static void main(String[] args) {
        List<Producer> producers = CoffeeGenerator.generateProducerList(100);

        System.out.println("Sorts by farm:");
        System.out.println(CoffeeService.getCoffeeCountByFarm(producers) + "\n");

        System.out.println("Countries with altitude > 1500 meters:");
        System.out.println(CoffeeService.getCountriesWithHighAltitude(producers) + "\n");

        System.out.println("Sorts by processing type:");
        System.out.println(CoffeeService.getSortsByProcessingType(producers));
    }
}
