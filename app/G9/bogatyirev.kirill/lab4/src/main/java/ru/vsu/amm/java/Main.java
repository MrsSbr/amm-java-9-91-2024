package ru.vsu.amm.java;

import java.io.IOException;

public class Main {
    public static final String FILE_PATH = "app/G9/bogatyirev.kirill/lab4/src/res";

    public static void main(String[] args) throws IOException {


        Tournaments tournaments = new Tournaments(FILE_PATH);
        for (Fight fight : tournaments.getFights()) {
            System.out.println(fight);
        }

        TournamentsManager tournamentsManager = new TournamentsManager();

        printAllFighters(tournamentsManager, tournaments);

        System.out.println(tournamentsManager.countOfWins(tournaments));

        System.out.println(tournamentsManager.monthWithMostFatality(tournaments));


    }

    public static void printAllFighters(TournamentsManager tournamentsManager, Tournaments tournaments) {
        int n = tournaments.getFights().getLast().getTournamentNumber();
        for (int i = 1; i < n; ++i) {
            System.out.println("Турнир номер " + i);
            System.out.println(tournamentsManager.allFightersInTournament(i, tournaments));
        }
    }

}