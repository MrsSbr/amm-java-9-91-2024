package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.Sacrifice;
import ru.vsu.amm.java.service.SacrificeStatsService;
import ru.vsu.amm.java.util.ArrayListOfSacrificeFactory;

import java.util.List;

public class SacrificeDemo {
    public static void main(String[] args) {
        List<Sacrifice> x = ArrayListOfSacrificeFactory.generateSacrifices(7243);

        System.out.println("Sacrifices with instant rain: " + SacrificeStatsService.CountInstantRainSacrifices(x));
        System.out.println("Last month without sacrifices: " + SacrificeStatsService.FindLastMonthWithoutSacrifices(x));

        var diff = SacrificeStatsService.CompareHumanAndAnimalSacrificesEfficiency(x);
        System.out.printf(
                "Human sacrifices are on average %.2f %% %s efficient than animal ones%n",
                Math.abs(diff * 100),
                diff < 0 ? "more" : "less"
        );
    }
}