package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.Family;
import ru.vsu.amm.java.entity.FlightData;
import ru.vsu.amm.java.util.GenerateFamilyUtil;


public class FlightDataService {

    public static String countPassengersFormatString(FlightData flightData) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = FlightData.MIN_NUMBER_FLIGHT; i <= FlightData.MAX_NUMBER_FLIGHT; i++) {
            int numberFlight = i;
            int sum = flightData.getFamilies()
                    .stream()
                    .filter(family -> family.numberFlight() == numberFlight)
                    .mapToInt(Family::countPeople)
                    .sum();
            stringBuilder.append(String.format("Flight №%d count passenger = %d\n", numberFlight, sum));
        }
        return stringBuilder.toString();
    }

    public static void fillRandomFamilies(FlightData flightData, int countFamily) {
        for (int i = 0; i < countFamily; i++) {
            flightData.getFamilies().add(GenerateFamilyUtil.generateFamily());
        }
    }
}
