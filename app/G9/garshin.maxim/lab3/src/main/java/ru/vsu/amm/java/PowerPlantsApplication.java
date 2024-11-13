package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.PowerPlant;
import ru.vsu.amm.java.service.PowerPlantService;
import ru.vsu.amm.java.util.PowerPlantFactory;

import java.util.List;
import java.util.Scanner;

public class PowerPlantsApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter amount of records:");
        int amount = scanner.nextInt();
        List<PowerPlant> records = PowerPlantFactory.generatePowerPlants(amount);


        System.out.println("\nList of types of power plants that have produced more than 50 MWh at least once in the last month");
        System.out.println(PowerPlantService.getTypesWithPowerOver50LastMonth(records));


        System.out.println("\nAverage power for each type of power plant in the last 3 months");
        PowerPlantService.getAveragePowerByTypeLastThreeMonthsVoid(records);
        PowerPlantService.getAveragePowerByTypeLastThreeMonths(records)
                .forEach((type, power) -> System.out.println(type + " " + power));


        System.out.println("\nTotal production power for the last year (for all types)");
        System.out.println(PowerPlantService.getTotalProductionLastYear(records));
    }
}