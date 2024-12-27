package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.BestTeaYear;
import ru.vsu.amm.java.entity.HaviestTeaBag;
import ru.vsu.amm.java.entity.TeaBag;
import ru.vsu.amm.java.enums.TeaType;
import ru.vsu.amm.java.service.TeaBagService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final int MIN_YEAR = 2000;
    private static final int MAX_YEAR = 2024;
    private static final int MIN_WEIGHT = 20;
    private static final int MAX_WEIGHT = 500;

    public static void main(String[] args) {
        List<TeaBag> teaBags = generateTeaBags(10000);

        System.out.println("The most productive year for each type:");
        List<BestTeaYear> bestTeaYears = TeaBagService.getBestTeaYears(teaBags);
        for (BestTeaYear bestTeaYear : bestTeaYears) {
            System.out.println(bestTeaYear.teaType() + ": " + bestTeaYear.year());
        }

        System.out.println();
        System.out.println("Tea types which were harvested in 2018:");
        TeaBagService.getTeaTypesInYear(teaBags, 2018).forEach(System.out::println);

        System.out.println();
        System.out.println("The heaviest tea bag for each type:");
        List<HaviestTeaBag> heaviestTeaBags = TeaBagService.getHaviestTeaBag(teaBags);
        for (HaviestTeaBag heaviestTeaBag : heaviestTeaBags) {
            System.out.println(heaviestTeaBag.teaType() + ": " + heaviestTeaBag.weight());
        }
    }

    private static List<TeaBag> generateTeaBags(int count) {
        List<TeaBag> teaBags = new ArrayList<>();
        TeaType[] types = TeaType.values();
        Random rand = new Random();

        for (int i = 0; i < count; i++) {
            int year = rand.nextInt(MAX_YEAR - MIN_YEAR) + MIN_YEAR;
            int weight = MIN_WEIGHT + rand.nextInt(MAX_WEIGHT - MIN_WEIGHT);
            int typeIndex = rand.nextInt(types.length);

            teaBags.add(new TeaBag(types[typeIndex], year, weight));
        }

        return teaBags;
    }
}
