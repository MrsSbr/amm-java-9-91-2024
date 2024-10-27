package ru.vsu.amm.java.sports.medals;

import java.util.List;

public class MedalApplication {
    private static final String PATH = "app/G9/shipilova.viktoria/lab4/medals.txt";

    public static void main(String[] args) {
        DataHandler dataHandler = new DataHandler();
        dataHandler.saveToFale(PATH);
        List<Medal> medals = dataHandler.loadFromFale(PATH);
        MedalAnalyzer medalAnalyzer = new MedalAnalyzer();

        System.out.println("\nTop 3 countries in the medal standings:");
        System.out.println(medalAnalyzer.topThreeInMedals(medals));
        System.out.println("\nMedalists in sports:");
        System.out.println(medalAnalyzer.athletesWhoTookPlaces(medals));
        System.out.println("\nBest Athlete:");
        List<String> topAthletes = medalAnalyzer.athleteWithTheMostMedals(medals);
        if (topAthletes.size() == 1) {
            System.out.println(topAthletes.get(0));
        } else {
            System.out.println("There is no outstanding athlete. Several athletes have the same number of medals.\n");
        }
    }
}