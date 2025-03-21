package ru.vsu.amm.java;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class CatGenerator {
    private static final List<String> NAMES = Arrays.asList("Odynanchik", "Adel", "Barsick", "Sharick", "Blinchik");

    public static List<CatWinner> generateWinners(int count) {
        List<CatWinner> winners = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            String name = NAMES.get(random.nextInt(NAMES.size()));
            Breed breed = Breed.values()[random.nextInt(Breed.values().length)];
            Gender gender = Gender.values()[random.nextInt(Gender.values().length)];
            winners.add(new CatWinner(name, breed, gender));
        }
        return winners;
    }
}
