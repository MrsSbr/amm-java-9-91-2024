package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CatExhibitionAnalyzer {

    public static double calculateGenderRatio (List<CatWinner> winners){
     long maleCount = winners.stream()
             .filter(cat -> cat.getGender() == Gender.MALE)
             .count();
     long femaleCount = winners.size() - maleCount;
     return femaleCount == 0 ? 0 : (double) maleCount/femaleCount;
    }

    public static List<String> calculateBreedStatistics(List<CatWinner> winners){
        List<String> statistics = new ArrayList<>();
        for(Breed breed : Breed.values()){
            long count = winners.stream()
                    .filter(cat -> cat.getBreed() == breed)
                    .count();
            statistics.add(breed + ": " + count);
        }
        return statistics;
    }

    public static List<String> getUniqueFemaleWinners(List<CatWinner> winners){
        return winners.stream()
        .filter(cat -> cat.getGender() == Gender.FEMALE)
                .map(CatWinner::getName)
                .distinct()
                .collect(Collectors.toList());

    }
}
