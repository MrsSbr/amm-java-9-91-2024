package ru.vsu.amm.java;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class Flight {
    private final int FLIGHT_MIN = 1;
    private final int FLIGHT_MAX = 11;
    private final int FAMILY_MIN = 1;
    private final int FAMILY_MAX = 9;
    private List<FamilyRecord> records;

    public Flight(int recNum) {
        records = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < recNum; ++i) {
            int flightNumber = random.nextInt(FLIGHT_MIN, FLIGHT_MAX);
            int familyAmount = random.nextInt(FAMILY_MIN, FAMILY_MAX);
            records.add(new FamilyRecord(flightNumber, familyAmount));
        }
    }

    public Flight(ArrayList<FamilyRecord> records) { this.records = records; }

    public void countPeopleByFlight() {
        records.stream()
                .collect(Collectors.groupingBy(FamilyRecord::flightNumber,
                        Collectors.summingInt(FamilyRecord::familyAmount)))
                .forEach((flight, count) -> System.out.println("flight " + flight + ": " + count + " passengers"));
    }

    public void addRecord(FamilyRecord record) { records.add(record); }

    public boolean removeRecord(FamilyRecord record) { return records.remove(record); }

    public void removeRecordByIndex(int index) { records.remove((index)); }

    public boolean containsRecord (FamilyRecord record) { return records.contains((record)); }

    public List<FamilyRecord> getRecords() { return new ArrayList<>(records); }
}