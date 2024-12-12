package ru.vsu.amm.java;

import ru.vsu.amm.java.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MatchesStatsGenerator {
    private final static String[] HOME_SURNAMES = {"Feshenko", "Lobanov", "Garshin", "Kavunov"};
    private final static String[] OWNER_SURNAMES = {"Nakamura", "Gavrilov", "Shipilov", "Anikandrov", "Gadziev"};


    public static List<Match> generateMatchRecords(int count) {
        List<Match> matchRecords = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            matchRecords.add(generateMatch());
        }
        return matchRecords;
    }

    private static Match generateMatch() {
        final Random R = new Random();
        String homeSurname = HOME_SURNAMES[R.nextInt(HOME_SURNAMES.length)];
        String ownerSurname = OWNER_SURNAMES[R.nextInt(OWNER_SURNAMES.length)];
        return new Match(homeSurname, ownerSurname);
    }
}
