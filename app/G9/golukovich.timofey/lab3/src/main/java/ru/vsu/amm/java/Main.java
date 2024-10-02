package ru.vsu.amm.java;

import java.time.LocalDate;

public class Main {
    private static final int ANCIENT_RECORDS_COUNT = 8573;
    private static LocalDate personDate = LocalDate.of(-34567, 8, 9);

    public static void main(String[] args) {
        var wall = new AncientWall(ANCIENT_RECORDS_COUNT);

        wall.findHunterNames().forEach(name -> System.out.print(name.toString() + " "));
        System.out.println();

        System.out.println(wall.sumRecentMammothWeight(personDate));
        System.out.println();

        wall.countMammothsForEachHunter().forEach((k, v) -> System.out.println(k + ": " + v));
    }
}