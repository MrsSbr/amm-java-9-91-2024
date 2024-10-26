package ru.vsu.amm.java.classes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NullFanVotesServiceTest extends FanVotesServiceTest {

    @Override
    @Test
    public void testFindMostPopularPlayers() {
        Assertions.assertThrows(NullPointerException.class,
                () -> fanVotesService.findMostPopularPlayers());
    }

    @Override
    @Test
    public void testFindPlayersWithoutVotes() {
        Assertions.assertThrows(NullPointerException.class,
                () -> fanVotesService.findPlayersWithoutVotes());
    }

    @Override
    @Test
    public void testFindVotedPlayers() {
        Assertions.assertThrows(NullPointerException.class,
                () -> fanVotesService.findVotedPlayers());
    }
}
