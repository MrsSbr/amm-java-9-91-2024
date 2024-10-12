package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.Sacrifice;
import ru.vsu.amm.java.service.SacrificeStatsService;
import ru.vsu.amm.java.util.SacrificeFactory;

import java.util.List;

public class SacrificeDemo {
    public static void main(String[] args) {
        List<Sacrifice> x = SacrificeFactory.generateSacrifice(7243);

        System.out.println("Sacrifices with instant rain: " + SacrificeStatsService.countInstantRainSacrifices(x));

        System.out.println("Last month without sacrifices: " + SacrificeStatsService.findLastMonthWithoutSacrifices(x));

        var diff = SacrificeStatsService.compareHumanAndAnimalSacrificesEfficiency(x);
        System.out.printf(
                "Human sacrifices are on average %.2f %% %s efficient than animal ones%n",
                Math.abs(diff * 100),
                diff < 0 ? "more" : "less"
        );
    }
}