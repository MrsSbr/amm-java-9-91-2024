package ru.vsu.amm.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.FanVote;
import ru.vsu.amm.java.service.FanVotesService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class FanVotesServiceTest {
    private static final FanVotesService avgFanVotesService = fillAverageFanVotes();
    private static final FanVotesService allPlayersMentionedFanVotesService =
            fillAllPlayersMentionedFanVotes();
    private static final FanVotesService emptyFanVotesService =
            makeEmptyFanVotesService();
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

    public static FanVotesService fillAllPlayersMentionedFanVotes() {
        FanVotesService allPlayersMentionedService = new FanVotesService();
        allPlayersMentionedService.setFanVote(List.of(
                new FanVote(Set.of(1, 2, 3)),
                new FanVote(Set.of(4, 5, 6)),
                new FanVote(Set.of(7, 8, 9)),
                new FanVote(Set.of(10, 11, 12)),
                new FanVote(Set.of(13, 14, 15)),
                new FanVote(Set.of(16, 17, 18)),
                new FanVote(Set.of(19, 20, 21)),
                new FanVote(Set.of(22))
        ));
        return allPlayersMentionedService;
    }

    public static FanVotesService makeEmptyFanVotesService() {
        FanVotesService emptyService = new FanVotesService();
        emptyService.setFanVote(new ArrayList<>());
        return emptyService;
    }

    @Test
    public void testFindMostPopularPlayersBasedOnAverageCollection() {
        Assertions.assertEquals(Set.of(2, 3),
                avgFanVotesService.findMostPopularPlayers());
    }

    @Test
    public void testFindPlayersWithoutVotesBasedOnAverageCollection() {
        Set<Integer> expectedCollection = new HashSet<>(Set.of(4, 7, 9));
        expectedCollection.addAll(IntStream.rangeClosed(11, MAX_VOTE)
                .boxed()
                .toList());
        Assertions.assertEquals(expectedCollection,
                avgFanVotesService.findPlayersWithoutVotes());
    }

    @Test
    public void testFindVotedPlayersBasedOnAverageCollection() {
        Assertions.assertEquals(Set.of(1, 2, 3, 5, 6, 8, 10),
                avgFanVotesService.findVotedPlayers());
    }

    @Test
    public void testFindMostPopularPlayersBasedOnAllPlayersMentionedCollection() {
        Set<Integer> expectedCollection =
                new HashSet<>(IntStream.rangeClosed(MIN_VOTE, MAX_VOTE)
                .boxed()
                .toList());
        Assertions.assertEquals(expectedCollection,
                allPlayersMentionedFanVotesService.findMostPopularPlayers());
    }

    @Test
    public void testFindPlayersWithoutVotesBasedOnAllPlayersMentionedCollection() {
        Assertions.assertEquals(Set.of(),
                allPlayersMentionedFanVotesService.findPlayersWithoutVotes());
    }

    @Test
    public void testFindVotedPlayersBasedOnAllPlayersMentionedCollection() {
        Set<Integer> expectedCollection =
                new HashSet<>(IntStream.rangeClosed(MIN_VOTE, MAX_VOTE)
                        .boxed()
                        .toList());
        Assertions.assertEquals(expectedCollection,
                allPlayersMentionedFanVotesService.findVotedPlayers());
    }

    @Test
    public void testFindMostPopularPlayersBasedOnEmptyCollection() {
        Set<Integer> expectedCollection =
                new HashSet<>(IntStream.rangeClosed(MIN_VOTE, MAX_VOTE)
                        .boxed()
                        .toList());
        Assertions.assertEquals(expectedCollection,
               emptyFanVotesService.findMostPopularPlayers());
    }

    @Test
    public void testFindPlayersWithoutVotesBasedOnEmptyCollection() {
        Set<Integer> expectedCollection =
                new HashSet<>(IntStream.rangeClosed(MIN_VOTE, MAX_VOTE)
                        .boxed()
                        .toList());

        Assertions.assertEquals(expectedCollection,
                emptyFanVotesService.findPlayersWithoutVotes());
    }

    @Test
    public void testFindVotedPlayersBasedOnEmptyCollection() {
        Assertions.assertEquals(Set.of(),
                emptyFanVotesService.findVotedPlayers());
    }
}
