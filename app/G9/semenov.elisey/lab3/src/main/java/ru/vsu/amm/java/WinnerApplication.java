package ru.vsu.amm.java;

import java.util.List;

public class WinnerApplication {
    public static void main(String[] args) {
        List<Winner> winners = DataGenerator.generateWinners(20);
        WinnerService winnerService = new WinnerService();

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