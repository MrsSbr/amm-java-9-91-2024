package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CatExhibitionAnalyzer {
    public static double calculateGenderRatio(List<CatWinner> winners) {

        if (winners == null) {
            throw new IllegalArgumentException("The list of winners can't be null");
        }

        for (CatWinner winner : winners) {
            if (winner.getName() == null || winner.getBreed() == null || winner.getGender() == null) {
                throw new IllegalArgumentException("Fields in CatWinner can't be null");
            }
        }

        if (winners.isEmpty()) {
            return 0;
        }

        long maleCount = winners.stream()
                .filter(cat -> cat.getGender() == Gender.MALE)
                .count();

        long femaleCount = winners.stream()
                .filter(cat -> cat.getGender() == Gender.FEMALE)
                .count();

        if (femaleCount == 0) {
            return Double.POSITIVE_INFINITY;  // Если женщин нет, возвращаем бесконечность
        }

        if (maleCount == 0) {
            return 0;
        }

        return (double) maleCount / femaleCount; //соотношение мужчин на женщин, double для дробных значений
    }

    public static List<String> calculateBreedStatistics(List<CatWinner> winners) {
        if (winners == null) {
            return new ArrayList<>();
        }

        for (CatWinner winner : winners) {
            if (winner.getName() == null || winner.getBreed() == null || winner.getGender() == null) {
                throw new IllegalArgumentException("Fields in CatWinner can't be null");
            }
        }

        List<String> statistics = new ArrayList<>();
        List<Breed> breeds = winners.stream()
                .map(CatWinner::getBreed)
                .distinct()
                .toList();

        for (Breed breed : breeds) {
            long count = winners.stream()
                    .filter(cat -> cat.getBreed() == breed)
                    .count();
            statistics.add(breed + ": " + count);
        }

        return statistics;
    }

    public static Set<String> getUniqueFemaleWinners(List<CatWinner> winners) {
        if (winners == null) {
            throw new IllegalArgumentException("The list of winners can't be null");
        }

        for (CatWinner winner : winners) {
            if (winner.getName() == null || winner.getBreed() == null || winner.getGender() == null) {
                throw new IllegalArgumentException("Fields in CatWinner can't be null");
            }
        }

        return winners.stream()
                .filter(cat -> cat.getGender() == Gender.FEMALE)
                .map(CatWinner::getName)
                .collect(Collectors.toSet());
    }

}
