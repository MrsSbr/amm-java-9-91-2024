package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.EggProductionRecord;
import ru.vsu.amm.java.enums.BirdType;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EggProductionService {
    EggProductionService() {
    }

    public static  Set<BirdType> getHighProductionBirdType(List<EggProductionRecord> records) {
        LocalDate threeWeeksAgo = LocalDate.now().minus(3, ChronoUnit.WEEKS);
            return records.stream()
                .filter(record -> record.date().isAfter(threeWeeksAgo) && record.eggsCount() > 20)
                .map(EggProductionRecord::birdType)
                .collect(Collectors.toSet());
    }

    public static Month getMostProductiveMonth(List<EggProductionRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        record->record.date().getMonth(),
                        Collectors.summingInt(EggProductionRecord::eggsCount)))
                .entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue()))
                .orElseThrow(()-> new IllegalStateException("Нет данных"))
                .getKey();
    }

    public static int getTotalEggsProduced(List<EggProductionRecord> records){
        return records.stream()
                .mapToInt(EggProductionRecord::eggsCount)
                .sum();
    }
}
