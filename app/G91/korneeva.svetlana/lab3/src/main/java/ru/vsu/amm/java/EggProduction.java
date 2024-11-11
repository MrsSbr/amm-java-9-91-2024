package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.EggProductionRecord;
import ru.vsu.amm.java.service.EggProductionService;
import ru.vsu.amm.java.util.EggProductionFactory;

import java.util.List;

public class EggProduction {
    public static void main(String[] args) {
        List<EggProductionRecord> records = EggProductionFactory.generateRecords(3412);

        var highProductionBirds = EggProductionService.getHighProductionBirdType(records);
        System.out.println("List of bird types that have produced more than 20 eggs at least once in the last 3 weeks: "
                        + highProductionBirds);

        var mostProductiveMonth = EggProductionService.getMostProductiveMonth(records);
        System.out.println("The most productive month: "
                + mostProductiveMonth);

        var totalEggs = EggProductionService.getTotalEggsProduced(records);
        System.out.println("Total number of eggs produced: "
                + totalEggs);
    }
}