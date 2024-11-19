package ru.vsu.amm.java;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AncientWallService {

    private List<AncientRecord> ancientRecords;

    public AncientWallService(AncientWall ancientWall) {
        this.ancientRecords = ancientWall.getRecords();
    }

    public List<HunterName> findHunterNames() {
        return ancientRecords.stream()
                .map(AncientRecord::getHunterName)
                .distinct()
                .collect(Collectors.toList());
    }

    public int sumRecentMammothWeight(LocalDate currentDate) {
        return ancientRecords.stream()
                .filter(rec -> rec.getDate().isAfter(currentDate.minusYears(3))
                        && rec.getDate().isBefore(currentDate.plusYears(3)))
                .mapToInt(AncientRecord::getMammothWeight).sum();
    }

    public Map<HunterName, Long> countMammothsForEachHunter() {
        return ancientRecords.stream()
                .collect(Collectors.groupingBy(AncientRecord::getHunterName,
                        Collectors.counting()));
    }
}
