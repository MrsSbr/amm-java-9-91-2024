package ru.vsu.amm.java;

import java.util.List;


public class FlightProcessing {

    public static void countPeopleByFlight(List<FamilyRecord> records) {
        records.stream()
                .map(FamilyRecord::flightNumber)
                .distinct()
                .sorted()
                .forEach(flightNumber -> {
                    int count = records.stream()
                            .filter(record -> record .flightNumber() == flightNumber)
                            .mapToInt(FamilyRecord::familyAmount)
                            .sum();
                    System.out.println("flight " + flightNumber + ": " + count + " passengers");
                });
    }

}