package ru.vsu.amm.java;

import java.util.List;
import java.util.ArrayList;

public class Flight {
    private final List<FamilyRecord> records;

    public Flight(int recNum) {
        records = new ArrayList<>();

        for (int i = 0; i < recNum; ++i) {
            records.add(RandomFamilyGenerator.generateRandomFamily());
        }
    }

    public Flight(ArrayList<FamilyRecord> records) { this.records = records; }

    public void addRecord(FamilyRecord record) { records.add(record); }

    public boolean removeRecord(FamilyRecord record) { return records.remove(record); }

    public void removeRecordByIndex(int index) { records.remove((index)); }

    public boolean containsRecord (FamilyRecord record) { return records.contains((record)); }

    public List<FamilyRecord> getRecords() { return new ArrayList<>(records); }
}
