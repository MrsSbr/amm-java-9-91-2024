package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.Family;
import ru.vsu.amm.java.entity.FlightData;
import ru.vsu.amm.java.util.GenerateFamilyUtil;


public class FlightDataService {

    public static void printCountPassengers(FlightData flightData) {
        for(int i = FlightData.MIN_NUMBER_FLIGHT; i <= FlightData.MAX_NUMBER_FLIGHT; i++) {
            int numberFlight = i;
            int sum = flightData.getFamilies()
                    .stream()
                    .filter(family -> family.numberFlight() == numberFlight)
                    .mapToInt(Family::count)
                    .sum();
            System.out.printf("Flight №%d count passenger = %d\n", numberFlight, sum);
        }
    }

    public static void fillRandomFamilies(FlightData flightData, int count) {
        for (int i = 0; i < count; i++) {
            flightData.getFamilies().add(GenerateFamilyUtil.generateFamily());
        }
    }
}
