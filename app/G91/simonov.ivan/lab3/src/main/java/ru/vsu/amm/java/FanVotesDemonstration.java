package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.FanVote;
import ru.vsu.amm.java.service.FanVotesService;

import java.util.List;

public class FanVotesDemonstration {

    private final static int VOTERS = 3000;

    public static void main(String[] args) {

        List<FanVote> fanVotes = FanVotesService.generateFanVotes(VOTERS);

        System.out.println("Номера самых популярных игроков:");
        System.out.println(FanVotesService.findMostPopularPlayers(fanVotes));

        System.out.println("Номера игроков, за которых голосовали:");
        System.out.println(FanVotesService.findVotedPlayers(fanVotes));

        System.out.println("Номера игроков, не получивших ни одного голоса:");
        System.out.println(FanVotesService.findPlayersWithoutVotes(fanVotes));
    }
}