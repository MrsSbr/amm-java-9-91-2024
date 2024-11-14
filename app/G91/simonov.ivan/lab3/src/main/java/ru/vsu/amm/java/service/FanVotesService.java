package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.FanVote;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FanVotesService {

    private final static int PERSONAL_VOTES = 3;
    public final static int MIN_VOTE = 1;
    public final static int MAX_VOTE = 22;

    private static FanVote generateFanVote(Random rnd) {

        Set<Integer> votes = new HashSet<>();
        while (votes.size() < PERSONAL_VOTES) {
            votes.add(rnd.nextInt(MIN_VOTE, MAX_VOTE + 1));
        }
        return new FanVote(votes);
    }

    public static List<FanVote> generateFanVotes(int cntVotes) {

        Random rnd = new Random();
        return Stream.generate(() -> generateFanVote(rnd))
                .limit(cntVotes)
                .toList();
    }

    public static Set<Integer> findMostPopularPlayers(List<FanVote> fanVotes) {

        List<Integer> groupedVotes =
                new ArrayList<>(Collections.nCopies(MAX_VOTE - MIN_VOTE + 1, 0));

        fanVotes.stream()
                .flatMap(i -> i.votes().stream())
                .forEach(i -> groupedVotes.set(i - MIN_VOTE, groupedVotes.get(i - MIN_VOTE) + 1));

        int maxPopularity = groupedVotes.stream()
                .max(Integer::compareTo)
                .orElse(0);

        return IntStream.rangeClosed(MIN_VOTE, MAX_VOTE)
                .filter(i -> groupedVotes.get(i - MIN_VOTE).equals(maxPopularity))
                .boxed()
                .collect(Collectors.toSet());
    }

    public static Set<Integer> findVotedPlayers(List<FanVote> fanVotes) {

        return fanVotes.stream()
                .flatMap(i -> i.votes().stream())
                .collect(Collectors.toSet());
    }

    public static Set<Integer> findPlayersWithoutVotes(List<FanVote> fanVotes) {

        return IntStream.rangeClosed(MIN_VOTE, MAX_VOTE)
                .filter(i -> !findVotedPlayers(fanVotes).contains(i))
                .boxed()
                .collect(Collectors.toSet());
    }
}
