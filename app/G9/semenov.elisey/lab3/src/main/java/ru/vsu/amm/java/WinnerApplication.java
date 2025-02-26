package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.Winner;
import ru.vsu.amm.java.enums.Department;

import java.util.List;

public class WinnerApplication {
    public static void main(String[] args) {
        List<Winner> winners = ru.vsu.amm.java.DataGenerator.generateWinners(20);
        ru.vsu.amm.java.WinnerService winnerService = new ru.vsu.amm.java.WinnerService();

        List<Department> departmentsWithMostWins = winnerService.getDepartmentsWithMostWins(winners);
        System.out.print("departmentsWithMostWins: ");
        for (Department d : departmentsWithMostWins) {
            System.out.print(d + " ");
        }
        System.out.println();

        System.out.print("uniqueWinners: ");
        List<String> uniqueWinners = winnerService.getUniqueWinners(winners);
        for (String w: uniqueWinners) {
            System.out.print(w + " ");
        }
        System.out.println();

        int oneTimeWinnersCount = winnerService.getOneTimeWinnersCount(winners);
        System.out.println("oneTimeWinnersCount: " + oneTimeWinnersCount);
    }
}