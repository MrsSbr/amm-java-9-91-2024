package ru.vsu.amm.java;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AncientWall {
    private final int MAX_HUNTING_DAY = -30000 * 365;
    private final int MIN_HUNTING_DAY = -40000 * 365;
    private final int MAX_MAMMOTH_WEIGHT = 500;
    private final int MIN_MAMMOTH_WEIGHT = 150;
    private List<AncientRecord> records;

    public AncientWall(int recordsCount) {
        records = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < recordsCount; ++i) {
            LocalDate date = LocalDate.ofEpochDay(
                    random.nextInt(MIN_HUNTING_DAY, MAX_HUNTING_DAY));
            int mammothWeight = random.nextInt(
                    MIN_MAMMOTH_WEIGHT, MAX_MAMMOTH_WEIGHT);
            int hunterNamesCount = HunterName.values().length;
            HunterName hunterName = HunterName
                    .values()[random.nextInt(hunterNamesCount)];

            records.add(new AncientRecord(date, mammothWeight, hunterName));
        }
    }

    public AncientWall(ArrayList<AncientRecord> records) {
        this.records = records;
    }

    public void addRecord(AncientRecord record) {
        records.add(record);
    }

    public boolean removeRecord(AncientRecord record) {
        return records.remove(record);
    }

    public void removeRecord(int index) {
        records.remove(index);
    }

    public boolean containsRecord(AncientRecord record) {
        return records.contains(record);
    }

    public List<AncientRecord> getRecords() {
        return new ArrayList<>(records);
    }
}
