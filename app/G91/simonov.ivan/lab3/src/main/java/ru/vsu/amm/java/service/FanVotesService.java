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

    private List<FanVote> fanVotes;

    public FanVotesService() {}

    public int getMinVote() {
        return MIN_VOTE;
    }

    public int getMaxVote() {
        return MAX_VOTE;
    }

    public void setFanVote(List<FanVote> fanVotes) {
        this.fanVotes = fanVotes;
    }

    private static FanVote generateFanVote() {

        Set<Integer> votes = new HashSet<>();
        while (votes.size() < PERSONAL_VOTES) {
            votes.add(RND.nextInt(MIN_VOTE, MAX_VOTE + 1));
        }
        return new FanVote(votes);
    }

    public void generateFanVotes(int cntVotes) {

        fanVotes = Stream.generate(FanVotesService::generateFanVote)
                .limit(cntVotes)
                .toList();
    }

    public Set<Integer> findMostPopularPlayers() {
        List<Integer> groupedVotes =
                new ArrayList<>(Collections.nCopies(MAX_VOTE - MIN_VOTE + 1, 0));

        fanVotes.stream()
                .flatMap(i -> i.getVotes().stream())
                .forEach(i -> groupedVotes.set(i - 1, groupedVotes.get(i - 1) + 1));

        int maxPopularity = groupedVotes.stream()
                .max(Integer::compareTo)
                .orElse(0);

        return IntStream.rangeClosed(MIN_VOTE, MAX_VOTE)
                .filter(i -> groupedVotes.get(i - 1).equals(maxPopularity))
                .boxed()
                .collect(Collectors.toSet());
    }

    public Set<Integer> findVotedPlayers() {

        return fanVotes.stream()
                .flatMap(i -> i.getVotes().stream())
                .collect(Collectors.toSet());
    }

    public Set<Integer> findPlayersWithoutVotes() {

        return IntStream.rangeClosed(MIN_VOTE, MAX_VOTE)
                .filter(i -> !findVotedPlayers().contains(i))
                .boxed()
                .collect(Collectors.toSet());
    }
}
