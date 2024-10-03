package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.Sacrifice;
import ru.vsu.amm.java.service.SacrificeStats;
import ru.vsu.amm.java.util.SacrificeFactory;

import java.util.ArrayList;
import java.util.List;

public class SacrificeDemo {
    public static void main(String[] args) {
        List<Sacrifice> x = new ArrayList<>(7243){};
        for (int i = 0; i < 7243; ++i) {
            x.add(SacrificeFactory.generateSacrifice());
            //System.out.println(x.get(i));
        }

        System.out.println("Sacrifices with instant rain: " + SacrificeStats.CountInstantRainSacrifices(x));
        System.out.println("Last month without sacrifices: " + SacrificeStats.FindLastMonthWithoutSacrifices(x));
        System.out.println(SacrificeStats.PrintSacrificeStats(x));
    }
}