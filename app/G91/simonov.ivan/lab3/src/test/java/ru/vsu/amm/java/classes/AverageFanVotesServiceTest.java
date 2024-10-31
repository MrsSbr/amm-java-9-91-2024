package ru.vsu.amm.java.classes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.FanVote;
import ru.vsu.amm.java.service.FanVotesService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class AverageFanVotesServiceTest extends FanVotesServiceTest {

    public AverageFanVotesServiceTest() {
        super();
        fanVotes = List.of(
                new FanVote(Set.of(1, 2, 3)),
                new FanVote(Set.of(5, 6, 3)),
                new FanVote(Set.of(1, 2, 3)),
                new FanVote(Set.of(5, 2, 8)),
                new FanVote(Set.of(10, 6, 8))
        );
    }

    @Override
    @Test
    public void testFindMostPopularPlayers() {
        Assertions.assertEquals(Set.of(2, 3),
                FanVotesService.findMostPopularPlayers(fanVotes));
    }

    @Override
    @Test
    public void testFindPlayersWithoutVotes() {
        Set<Integer> expectedCollection = new HashSet<>(Set.of(4, 7, 9));
        expectedCollection.addAll(IntStream.rangeClosed(11, MAX_VOTE)
                .boxed()
                .toList());
        Assertions.assertEquals(expectedCollection,
                FanVotesService.findPlayersWithoutVotes(fanVotes));
    }

    @Override
    @Test
    public void testFindVotedPlayers() {
        Assertions.assertEquals(Set.of(1, 2, 3, 5, 6, 8, 10),
                FanVotesService.findVotedPlayers(fanVotes));
    }
}
