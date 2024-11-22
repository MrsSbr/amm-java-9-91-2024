package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.FlightData;
import ru.vsu.amm.java.service.FlightDataService;

public class AirportApplication {
    private static final int COUNT_GENERATE_FAMILY = 50;
    public static void main(String[] args) {
        FlightData flightData = new FlightData();

        FlightDataService.fillRandomFamilies(flightData, COUNT_GENERATE_FAMILY);
        FlightDataService.printCountPassengers(flightData);
    }
}