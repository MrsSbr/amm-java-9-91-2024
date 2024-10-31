package ru.vsu.amm.java.classes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.service.FanVotesService;

public class NullFanVotesServiceTest extends FanVotesServiceTest {

    @Override
    @Test
    public void testFindMostPopularPlayers() {
        Assertions.assertThrows(NullPointerException.class,
                () -> FanVotesService.findMostPopularPlayers(fanVotes));
    }

    @Override
    @Test
    public void testFindPlayersWithoutVotes() {
        Assertions.assertThrows(NullPointerException.class,
                () -> FanVotesService.findPlayersWithoutVotes(fanVotes));
    }

    @Override
    @Test
    public void testFindVotedPlayers() {
        Assertions.assertThrows(NullPointerException.class,
                () -> FanVotesService.findVotedPlayers(fanVotes));
    }
}
