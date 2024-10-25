package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.FanVote;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FanVotesService {

    private final static Random RND = new Random();
    private final static int PERSONAL_VOTES = 3;
    private final static int MIN_VOTE = 1;
    private final static int MAX_VOTE = 22;

    public static int getMinVote() {
        return MIN_VOTE;
    }

    public static int getMaxVote() {
        return MAX_VOTE;
    }

    private static FanVote generateFanVote() {
        Set<Integer> votes = new HashSet<>();
        while (votes.size() < PERSONAL_VOTES) {
            votes.add(RND.nextInt(MIN_VOTE, MAX_VOTE + 1));
        }
        return new FanVote(votes);
    }

    public static List<FanVote> generateFanVotes(int cntVotes) {
        return Stream.generate(FanVotesService::generateFanVote)
                .limit(cntVotes)
                .toList();
    }

    public static Set<Integer> findMostPopularPlayers(List<FanVote> fanVotes) {

        if (fanVotes == null) {
            throw new NullPointerException();
        }

        List<Integer> groupedVotes =
                new ArrayList<>(Collections.nCopies(MAX_VOTE - MIN_VOTE + 1, 0));

        fanVotes.stream()
                .flatMap(i -> i.getVotes().stream())
                .forEach(i -> groupedVotes.set(i - 1, groupedVotes.get(i - 1) + 1));

        Integer maxFrequency = groupedVotes.stream().max(Integer::compareTo).orElse(0);

        return IntStream.rangeClosed(MIN_VOTE, MAX_VOTE)
                .filter(i -> groupedVotes.get(i - 1).equals(maxFrequency))
                .boxed()
                .collect(Collectors.toSet());
    }

    public static Set<Integer> findVotedPlayers(List<FanVote> fanVotes) {

        if (fanVotes == null) {
            throw new NullPointerException();
        }

        return fanVotes.stream()
                .flatMap(i -> i.getVotes().stream())
                .collect(Collectors.toSet());
    }

    public static Set<Integer> findPlayersWithoutVotes(List<FanVote> fanVotes) {

        if (fanVotes == null) {
            throw new NullPointerException();
        }

        List<Integer> allPlayers = IntStream.rangeClosed(MIN_VOTE, MAX_VOTE)
                .boxed()
                .toList();

        return allPlayers.stream()
                .filter(i -> !findVotedPlayers(fanVotes).contains(i))
                .collect(Collectors.toSet());
    }
}
