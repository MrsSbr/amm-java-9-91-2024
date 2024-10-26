package ru.vsu.amm.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.FanVote;
import ru.vsu.amm.java.service.FanVotesService;

import java.util.List;
import java.util.Set;

public class FanVotesServiceTest {
    private final FanVotesService avgFanVotesService = fillAverageFanVotes();

    public FanVotesService fillAverageFanVotes() {
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
        Assertions.assertEquals(Set.of(4, 7, 9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 ,21, 22),
                avgFanVotesService.findPlayersWithoutVotes());
    }

    @Test
    public void testFindVotedPlayers() {
        Assertions.assertEquals(Set.of(1, 2, 3, 5, 6, 8, 10),
                avgFanVotesService.findVotedPlayers());
    }
}
