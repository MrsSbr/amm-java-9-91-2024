package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.MatchRecord;
import ru.vsu.amm.java.service.MatchRecordService;
import ru.vsu.amm.java.util.MatchRecordFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class MatchApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a number of records: ");
        int n = scanner.nextInt();

        List<MatchRecord> records = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            records.add(MatchRecordFactory.generateRecord());
        }

        MatchRecordService matchRecordService = new MatchRecordService();

        System.out.print("Most frequent best players: ");
        Set<String> mostFrequentBestPlayers = matchRecordService.getMostFrequentBestPlayers(records);
        mostFrequentBestPlayers.forEach(player -> System.out.print(player + ", "));
        System.out.println("\n");

        System.out.print("Best players in away games: ");
        Set<String> bestAwayPlayers = matchRecordService.getBestPlayersInAwayGames(records);
        bestAwayPlayers.forEach(player -> System.out.print(player + ", "));
        System.out.println("\n");

        System.out.print("Players who became best only once: ");
        long bestOnlyOnce = matchRecordService.getSingleAwardedPlayersCount(records);
        System.out.println(bestOnlyOnce);
    }
}
