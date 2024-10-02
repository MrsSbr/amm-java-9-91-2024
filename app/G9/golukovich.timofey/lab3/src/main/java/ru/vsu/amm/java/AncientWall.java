package ru.vsu.amm.java;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class AncientWall {
    private int MAX_HUNTING_DAY = -30000 * 365;
    private int MIN_HUNTING_DAY = -40000 * 365;
    private int MAX_MAMMOTH_WEIGHT = 500;
    private int MIN_MAMMOTH_WEIGHT = 150;
    private ArrayList<AncientRecord> records;

    public AncientWall(int recordsCount) {
        records = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < recordsCount; ++i) {
            LocalDate date = LocalDate.ofEpochDay(
                    random.nextInt(MIN_HUNTING_DAY, MAX_HUNTING_DAY));
            int mammothWeight = random.nextInt(
                    MIN_MAMMOTH_WEIGHT, MAX_MAMMOTH_WEIGHT);
            HunterName hunterName = HunterName
                    .values()[random.nextInt(HunterName.values().length)];

            records.add(new AncientRecord(date, mammothWeight, hunterName));
        }
    }

    public AncientWall(ArrayList<AncientRecord> records) {
        this.records = records;
    }

    public List<HunterName> findHunterNames() {
        return records.stream()
                .map(rec -> rec.getHunterName())
                .distinct()
                .collect(Collectors.toList());
    }

    public int sumRecentMammothWeight(LocalDate currentDate) {
        return records.stream()
                .filter(rec -> rec.getDate().isAfter(currentDate.minusYears(3))
                        && rec.getDate().isBefore(currentDate.plusYears(3)))
                .map(rec -> rec.getMammothWeight())
                .reduce((sum, w) -> sum + w)
                .get();
    }

    public Map<HunterName, Long> countMammothsForEachHunter() {
        return records.stream()
                .collect(Collectors.groupingBy(rec -> rec.getHunterName(),
                        Collectors.counting()));
    }
}
