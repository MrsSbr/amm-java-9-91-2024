package ru.vsu.amm.java;

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
        List<TeaBag> TeaBags = generateTeaBags(10000);

        System.out.println("The most productive year for each type:");
        int[] bestYearsOfType = TeaBagService.getBestYearsOfType(TeaBags);
        for (int i = 0; i < bestYearsOfType.length; i++) {
            System.out.println(TeaType.values()[i] + ": " + bestYearsOfType[i]);
        }

        System.out.println();
        System.out.println("Tea types which were harvested in 2018:"); // строку можно и внутри функции написать
        TeaBagService.getTeaTypesInYear(TeaBags, 2018).forEach(System.out::println);

        System.out.println();
        System.out.println("The haviest tea bag for each type:");
        int[] haviestTeaBags = TeaBagService.getHaviestTeaBag(TeaBags);
        for (int i = 0; i < haviestTeaBags.length; i++) {
            System.out.println(TeaType.values()[i] + ": " + haviestTeaBags[i]);
        }
    }

    private static List<TeaBag> generateTeaBags(int count) {
        List<TeaBag> teaBags = new ArrayList<TeaBag>();
        TeaType[] types = TeaType.values();

        int year;
        int weight;
        int typeIndex;

        for (int i = 0; i < count; i++) {
            Random rand = new Random();
            year = rand.nextInt(MAX_YEAR - MIN_YEAR) + MIN_YEAR;
            weight = MIN_WEIGHT + rand.nextInt(MAX_WEIGHT - MIN_WEIGHT);
            typeIndex = rand.nextInt(types.length);

            teaBags.add(new TeaBag(types[typeIndex], year, weight));
        }
        return teaBags;

    }
}