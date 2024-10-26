package ru.vsu.amm.java;

import ru.vsu.amm.java.service.FanVotesService;

public class FanVotesDemonstration {

    private final static int VOTERS = 3000;

    public static void main(String[] args) {

        FanVotesService fanVotesService = new FanVotesService();
        fanVotesService.generateFanVotes(VOTERS);

        System.out.println("Номера самых популярных игроков:");
        System.out.println(fanVotesService.findMostPopularPlayers());

        System.out.println("Номера игроков, за которых голосовали:");
        System.out.println(fanVotesService.findVotedPlayers());
    }
}