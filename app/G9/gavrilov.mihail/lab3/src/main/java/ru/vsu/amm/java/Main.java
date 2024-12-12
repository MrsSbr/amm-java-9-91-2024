package ru.vsu.amm.java;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите число записей футбольных матчей: ");
        int numberOfRecords = sc.nextInt();
        List<Match> matchRecord = MatchesStatsGenerator.generateMatchRecords(numberOfRecords);

        System.out.println("Список всех MVP в матчах: \n");
        for (Match elem : matchRecord) {
            System.out.println(elem.getHomeBestPlayer() + ' ' +
                    elem.getAwayBestPlayer());
        }

        System.out.println("самые частые MVP: "
                + PlayerService.findMostFrequentPlayers(matchRecord));

        System.out.println("количество игроков, которые были MVP всего 1 раз: "
                + PlayerService.findPlayersBestOnce(matchRecord));

        System.out.println("игрок, которые были MVP на выездных матчах: "
                + PlayerService.findAwayBestPlayers(matchRecord));
    }
}