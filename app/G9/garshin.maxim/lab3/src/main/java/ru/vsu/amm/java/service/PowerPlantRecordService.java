package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.PowerPlantRecord;
import ru.vsu.amm.java.enums.Type;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PowerPlantRecordService {
    public Set<Type> getTypesWithProductionOver50MWLastMonth(List<PowerPlantRecord> records) {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        Set<Type> types = new HashSet<>();

        for (PowerPlantRecord record : records) {
            if (record.getDate().isAfter(oneMonthAgo) && record.getPower() > 50) {
                types.add(record.getType());
            }
        }

        return types;
    }

    public Map<Type, Double> getAveragePowerLastThreeMonths(List<PowerPlantRecord> records) {
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        Map<Type, Double> averagePowerMap = new HashMap<>();
        Map<Type, Integer> totalPowerMap = new HashMap<>();
        Map<Type, Integer> countMap = new HashMap<>();

        for (PowerPlantRecord record : records) {
            if (record.getDate().isAfter(threeMonthsAgo)) {
                Type type = record.getType();
                int power = record.getPower();

                totalPowerMap.put(type, totalPowerMap.getOrDefault(type, 0) + power);
                countMap.put(type, countMap.getOrDefault(type, 0) + 1);
            }
        }
        for (Type type : totalPowerMap.keySet()) {
            double average = (double) totalPowerMap.get(type) / countMap.get(type);
            averagePowerMap.put(type, average);
        }
        return averagePowerMap;
    }


    /*public void printAveragePowerLastThreeMonths(List<PowerPlantRecord> records) {
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        List<Type> types = new ArrayList<>();
        List<Integer> totalPowers = new ArrayList<>();
        List<Double> counts = new ArrayList<>();

        for (PowerPlantRecord record : records) {
            if (record.getDate().isAfter(threeMonthsAgo)) {
                int index = types.indexOf(record.getType());
                if (index == -1) {
                    types.add(record.getType());
                    totalPowers.add(record.getPower());
                    counts.add(1.0);
                } else {
                    totalPowers.set(index, totalPowers.get(index) + record.getPower());
                    counts.set(index, counts.get(index) + 1);
                }
            }
        }

        for (int i = 0; i < types.size(); ++i) {
            double averagePower = totalPowers.get(i) / counts.get(i);
            System.out.println(types.get(i) + ": " + averagePower);
        }
    }*/




    public int getTotalPowerLastYear(List<PowerPlantRecord> records) {
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        int totalPower = 0;
        for (PowerPlantRecord record : records) {
            if (record.getDate().isAfter(oneYearAgo)) {
                totalPower += record.getPower();
            }
        }

        return totalPower;
    }
}