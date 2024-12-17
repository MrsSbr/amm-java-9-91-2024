package ru.vsu.amm.java;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {

        String filePath = "app/G9/bogatyirev.kirill/lab4/src/main/java/ru/vsu/amm/java/res";

        Tournaments tournaments = new Tournaments(filePath);
        for(Fight fight : tournaments.getFights()) {
            System.out.println(fight);
        }

        TournamentsManager tournamentsManager = new TournamentsManager(tournaments);

        printAllFighters(tournamentsManager);

        System.out.println(tournamentsManager.countOfWins());

        System.out.println(tournamentsManager.monthWithMostFatality());


    }

    public static void printAllFighters(TournamentsManager tournamentsManager) {
        int n = tournamentsManager.getFights().getLast().getTournamentNumber();
        for(int i = 1; i < n; ++i) {
            System.out.println("Турнир номер " + i);
            System.out.println(tournamentsManager.allFightersInTournament(i));
        }
    }

}