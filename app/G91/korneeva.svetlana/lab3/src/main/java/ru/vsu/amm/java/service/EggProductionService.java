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
        if(records == null){
            throw new IllegalArgumentException("Записи не могут быть null");
        }
        LocalDate threeWeeksAgo = LocalDate.now().minus(3, ChronoUnit.WEEKS);
            return records.stream()
                .filter(record -> record.date().isAfter(threeWeeksAgo) && record.eggsCount() > 20)
                .map(EggProductionRecord::birdType)
                .collect(Collectors.toSet());
    }

    public static Month getMostProductiveMonth(List<EggProductionRecord> records) {
        if(records == null){
            throw new IllegalArgumentException("Записи не могут быть null");
        }
        if(records.isEmpty()){
            throw new IllegalArgumentException("Нет данных.");
        }
        int[] monthlyEggs = new int[12];
        records.stream().forEach( record -> {
            int monthIndex = record.date().getMonthValue() - 1;
            monthlyEggs[monthIndex]+=record.eggsCount();
        });
        int maxIndex = 0;
        for (int i = 0; i < monthlyEggs.length; i++){
            if(monthlyEggs[i] > monthlyEggs[maxIndex]){
                maxIndex = i;
            }
        }
        return Month.of(maxIndex+1);
    }

    public static int getTotalEggsProduced(List<EggProductionRecord> records){
        if(records == null){
            throw new IllegalArgumentException("Записи не могут быть null");
        }
        return records.stream()
                .mapToInt(EggProductionRecord::eggsCount)
                .sum();
    }
}
