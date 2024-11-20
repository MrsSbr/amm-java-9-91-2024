package ru.vsu.amm.java;

import java.util.List;

public class TaskCatExhibition {
    public static void main(String[] args) {
        List<CatWinner> winners = CatGenerator.generateWinners(20);

        double genderRatio = CatExhibitionAnalyzer.calculateGenderRatio(winners);
        System.out.println( "The ratio of cats to cats:" + genderRatio);

        List<String> breedStats = CatExhibitionAnalyzer.calculateBreedStatistics(winners);
        System.out.println( "Statistics of wins by cat breeds:");
        breedStats.forEach((System.out::println));

        List<String> uniqueFemaleWinners = CatExhibitionAnalyzer.getUniqueFemaleWinners(winners);
        System.out.println( "List of cats that have won at least once:" + uniqueFemaleWinners);


    }
}