package ru.vsu.amm.java.interfaces;

import org.junit.jupiter.api.Test;

public interface Testable {
    @Test
    void testFindMostPopularPlayers();

    @Test
    void testFindPlayersWithoutVotes();

    @Test
    void testFindVotedPlayers();
}
