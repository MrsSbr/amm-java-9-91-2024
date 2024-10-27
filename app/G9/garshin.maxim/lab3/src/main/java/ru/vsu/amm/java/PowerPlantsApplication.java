package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.PowerPlantRecord;
import ru.vsu.amm.java.service.PowerPlantRecordService;
import ru.vsu.amm.java.util.PowerPlantRecordFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PowerPlantsApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter amount of records:");
        int amount = scanner.nextInt();
        List<PowerPlantRecord> records = new ArrayList<>();

        for (int i = 0; i < amount; ++i) {
            records.add(PowerPlantRecordFactory.generateRecord());
        }

        PowerPlantRecordService powerPlantRecordService = new PowerPlantRecordService();

        System.out.println("\nList of types of power plants that have produced more than 50 MWh at least once in the last month");
        System.out.println(powerPlantRecordService.getTypesWithProductionOver50MWLastMonth(records));


        System.out.println("\nAverage power for each type of power plant in the last 3 months");
        //powerPlantRecordService.printAveragePowerLastThreeMonths(records);
        System.out.println(powerPlantRecordService.getAveragePowerLastThreeMonths(records));


        System.out.println("\nTotal production power for the last year (for all types)");
        System.out.println(powerPlantRecordService.getTotalPowerLastYear(records));
    }
}