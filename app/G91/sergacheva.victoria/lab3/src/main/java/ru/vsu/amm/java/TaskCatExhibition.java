package ru.vsu.amm.java;

import java.util.List;
import java.util.Set;

public class TaskCatExhibition {
    public static void main(String[] args) {
        try {
            List<CatWinner> winners = CatGenerator.generateWinners(20);
            double genderRatio = CatExhibitionAnalyzer.calculateGenderRatio(winners);
            System.out.println("The ratio of male to female cats: " + genderRatio);

            List<String> breedStats = CatExhibitionAnalyzer.calculateBreedStatistics(winners);
            System.out.println("Statistics of wins by cat breeds:");
            breedStats.forEach(System.out::println);

            Set<String> uniqueFemaleWinners = CatExhibitionAnalyzer.getUniqueFemaleWinners(winners);
            System.out.println("List of unique female cat winners: " + uniqueFemaleWinners);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
