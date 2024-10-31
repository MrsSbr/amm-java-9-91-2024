package ru.vsu.amm.java.classes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.service.FanVotesService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class EmptyFanVotesServiceTest extends FanVotesServiceTest {

    public EmptyFanVotesServiceTest() {
        fanVotes = new ArrayList<>();
    }

    @Override
    @Test
    public void testFindMostPopularPlayers() {
        Set<Integer> expectedCollection =
                new HashSet<>(IntStream.rangeClosed(MIN_VOTE, MAX_VOTE)
                        .boxed()
                        .toList());
        Assertions.assertEquals(expectedCollection,
                FanVotesService.findMostPopularPlayers(fanVotes));
    }

    @Override
    @Test
    public void testFindPlayersWithoutVotes() {
        Set<Integer> expectedCollection =
                new HashSet<>(IntStream.rangeClosed(MIN_VOTE, MAX_VOTE)
                        .boxed()
                        .toList());
        Assertions.assertEquals(expectedCollection,
                FanVotesService.findPlayersWithoutVotes(fanVotes));
    }

    @Override
    @Test
    public void testFindVotedPlayers() {
        Assertions.assertEquals(Set.of(),
                FanVotesService.findVotedPlayers(fanVotes));
    }
}
