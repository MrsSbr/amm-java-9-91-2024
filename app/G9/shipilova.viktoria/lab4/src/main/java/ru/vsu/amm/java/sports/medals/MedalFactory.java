package ru.vsu.amm.java.sports.medals;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class MedalFactory {
    private static final String[] begNames = {"Nat", "Ben", "Nick", "Ro", "Ad", "And", "Gabri", "Ann", "Veron"};
    private static final String[] endNames = {"sa", "", "an", "el", "ett", "ia", "fer", "ra", "er", "son"};
    private static final String[] begSurname = {"Wil", "Rob", "Tayl", "Ev"};
    private static final String[] endSurname = {"iams", "erts", "or", "ans"};

    public static List<Medal> generateMedal() {
        List<Medal> medals = new ArrayList<Medal>();
        Random random = new Random();
        for (KindOfSport sport : KindOfSport.values()) {
            for (int i = 1; i < 4; i++) {
                Country country = Country.values()[random.nextInt(Country.values().length)];
                String athlete = begNames[random.nextInt(begNames.length)] + endNames[random.nextInt(endNames.length)] + " " +
                        begSurname[random.nextInt(begSurname.length)] + endSurname[random.nextInt(endSurname.length)];
                medals.add(new Medal(country, sport, athlete, i));
            }
        }
        return medals;
    }
}
