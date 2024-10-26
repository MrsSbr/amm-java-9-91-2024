package ru.vsu.amm.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.FanVote;
import ru.vsu.amm.java.service.FanVotesService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class FanVotesServiceTest {
    private static final FanVotesService avgFanVotesService = fillAverageFanVotes();
    private final static int MIN_VOTE = avgFanVotesService.getMinVote();
    private final static int MAX_VOTE = avgFanVotesService.getMaxVote();

    public static FanVotesService fillAverageFanVotes() {
        FanVotesService avgService = new FanVotesService();
        avgService.setFanVote(List.of(
                new FanVote(Set.of(1, 2, 3)),
                new FanVote(Set.of(5, 6, 3)),
                new FanVote(Set.of(1, 2, 3)),
                new FanVote(Set.of(5, 2, 8)),
                new FanVote(Set.of(10, 6, 8))
        ));
        return avgService;
    }

    @Test
    public void testFindMostPopularPlayersBasedOnAverageCollection() {
        Assertions.assertEquals(Set.of(2, 3),
                avgFanVotesService.findMostPopularPlayers());
    }

    @Test
    public void testFindPlayersWithoutVotes() {
        Set<Integer> expectedCollection = new HashSet<>(Set.of(4, 7, 9));
        expectedCollection.addAll(IntStream.rangeClosed(11, MAX_VOTE)
                .boxed()
                .toList());
        Assertions.assertEquals(expectedCollection,
                avgFanVotesService.findPlayersWithoutVotes());
    }

    @Test
    public void testFindVotedPlayers() {
        Assertions.assertEquals(Set.of(1, 2, 3, 5, 6, 8, 10),
                avgFanVotesService.findVotedPlayers());
    }
}
