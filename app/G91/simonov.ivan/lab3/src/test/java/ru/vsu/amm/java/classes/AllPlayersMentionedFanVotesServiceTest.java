package ru.vsu.amm.java.classes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.FanVote;
import ru.vsu.amm.java.service.FanVotesService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class AllPlayersMentionedFanVotesServiceTest extends FanVotesServiceTest {

    public AllPlayersMentionedFanVotesServiceTest() {
        super();
        fanVotes = List.of(
                new FanVote(Set.of(1, 2, 3)),
                new FanVote(Set.of(4, 5, 6)),
                new FanVote(Set.of(7, 8, 9)),
                new FanVote(Set.of(10, 11, 12)),
                new FanVote(Set.of(13, 14, 15)),
                new FanVote(Set.of(16, 17, 18)),
                new FanVote(Set.of(19, 20, 21)),
                new FanVote(Set.of(22))
        );
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
        Assertions.assertEquals(Set.of(),
                FanVotesService.findPlayersWithoutVotes(fanVotes));
    }

    @Override
    @Test
    public void testFindVotedPlayers() {
        Set<Integer> expectedCollection =
                new HashSet<>(IntStream.rangeClosed(MIN_VOTE, MAX_VOTE)
                        .boxed()
                        .toList());
        Assertions.assertEquals(expectedCollection,
                FanVotesService.findVotedPlayers(fanVotes));
    }
}
