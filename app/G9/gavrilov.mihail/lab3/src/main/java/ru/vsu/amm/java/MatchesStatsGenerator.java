package ru.vsu.amm.java;

import ru.vsu.amm.java.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MatchesStatsGenerator {
    private static final Random RANDOM = new Random();
    private static final String[] HOME_SURNAMES = {"Feshenko", "Lobanov", "Garshin", "Kavunov"};
    private static final String[] OWNER_SURNAMES = {"Nakamura", "Gavrilov", "Shipilov", "Anikandrov", "Gadziev"};


    public static List<Match> generateMatchRecords(int count) {
        List<Match> matchRecords = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            matchRecords.add(generateMatch());
        }
        return matchRecords;
    }

    private static Match generateMatch() {
        String homeSurname = HOME_SURNAMES[RANDOM.nextInt(HOME_SURNAMES.length)];
        String ownerSurname = OWNER_SURNAMES[RANDOM.nextInt(OWNER_SURNAMES.length)];
        return new Match(homeSurname, ownerSurname);
    }
}
