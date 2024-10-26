package ru.vsu.amm.java.classes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class EmptyFanVotesServiceTest extends FanVotesServiceTest {

    public EmptyFanVotesServiceTest() {
        super();
        fanVotesService.setFanVote(new ArrayList<>());
    }

    @Override
    @Test
    public void testFindMostPopularPlayers() {
        Set<Integer> expectedCollection =
                new HashSet<>(IntStream.rangeClosed(MIN_VOTE, MAX_VOTE)
                        .boxed()
                        .toList());
        Assertions.assertEquals(expectedCollection,
                fanVotesService.findMostPopularPlayers());
    }

    @Override
    @Test
    public void testFindPlayersWithoutVotes() {
        Set<Integer> expectedCollection =
                new HashSet<>(IntStream.rangeClosed(MIN_VOTE, MAX_VOTE)
                        .boxed()
                        .toList());
        Assertions.assertEquals(expectedCollection,
                fanVotesService.findPlayersWithoutVotes());
    }

    @Override
    @Test
    public void testFindVotedPlayers() {
        Assertions.assertEquals(Set.of(),
                fanVotesService.findVotedPlayers());
    }
}
