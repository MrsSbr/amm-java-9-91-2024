package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.Sacrifice;
import ru.vsu.amm.java.service.SacrificeStatsService;
import ru.vsu.amm.java.util.ArrayListOfSacrificeFactory;

import java.util.List;

public class SacrificeDemo {
    public static void main(String[] args) {
        List<Sacrifice> x = ArrayListOfSacrificeFactory.generateSacrifices(7243);

        SacrificeStatsService sacrificeStatsService = new SacrificeStatsService(x);
        sacrificeStatsService.CollectStats();
        sacrificeStatsService.PrintStats();
    }
}