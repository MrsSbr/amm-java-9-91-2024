package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.FanVote;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

public class FanVotesService {

    private final static Random RND = new Random();
    private final static int PERSONAL_VOTES = 3;
    private final static int MIN_VOTE = 1;
    private final static int MAX_VOTE = 22;

    private List<FanVote> fanVotes;

    public List<FanVote> getFanVote() {
        return fanVotes;
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
}
