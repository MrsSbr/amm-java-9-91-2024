import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Match;
import ru.vsu.amm.java.PlayerService;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatchTest {

    @Test
    @DisplayName("Лучшие игроки по частоте, если список пуст")
    void testMostFrequentPlayersEmpty(){
        List<Match> matches = List.of();

        List<String> expected =  List.of();

        List<String> actual = PlayerService.findMostFrequentPlayers(matches);

        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    @DisplayName("Лучшие игроки по частоте, если такой один")
    void testMostFrequentPlayersOnlyOne(){
        List<Match> matches = List.of(
                new Match("Filatov", "Feshenko"),
                new Match("Filatov", "Anikandrov"),
                new Match("Anikandrov", "Tupikov"),
                new Match("Feshenko", "Filatov"));

        List<String> expected =  List.of("Filatov");

        List<String> actual = PlayerService.findMostFrequentPlayers(matches);

        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    @DisplayName("Лучшие игроки по частоте, если таких много")
    void testMostFrequentPlayersMany(){
        List<Match> matches = List.of(
                new Match("Filatov", "Feshenko"),
                new Match("Filatov", "Anikandrov"),
                new Match("Anikandrov", "Tupikov"),
                new Match("Feshenko", "Kostrikin"),
                new Match("Tupikov", "Arhipov"));

        List<String> expected =  List.of("Filatov", "Feshenko", "Tupikov", "Anikandrov");

        List<String> actual = PlayerService.findMostFrequentPlayers(matches);

        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    @DisplayName("Лучшие игроки выездных матчей, если список пуст")
    void testAwayBestPlayersEmpty(){
        List<Match> matches = List.of();

        Set<String> expected =  Set.of();

        Set<String> actual = PlayerService.findAwayBestPlayers(matches);

        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }
    @Test
    @DisplayName("Лучшие игроки выездных матчей, если такой один")
    void testAwayBestPlayersOnlyOne(){
        List<Match> matches = List.of(
                new Match("Filatov", "Kostrikin"),
                new Match("Filatov", "Kostrikin"),
                new Match("Anikandrov", "Kostrikin"),
                new Match("Feshenko", "Kostrikin"),
                new Match("Tupikov", "Kostrikin"));

        Set<String> expected =  Set.of("Kostrikin");

        Set<String> actual = PlayerService.findAwayBestPlayers(matches);

        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    @DisplayName("Лучшие игроки выездных матчей, если таких несколько")
    void testAwayBestPlayersOnlyMany(){
        List<Match> matches = List.of(
                new Match("Filatov", "Anikandrov"),
                new Match("Filatov", "Kostrikin"),
                new Match("Anikandrov", "Tupikov"),
                new Match("Feshenko", "Kostrikin"),
                new Match("Tupikov", "Kostrikin"));

        Set<String> expected =  Set.of("Kostrikin", "Anikandrov", "Tupikov");

        Set<String> actual = PlayerService.findAwayBestPlayers(matches);

        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    @DisplayName("Количество игроков, которые становились лучшими всего один раз, " +
            "если нет ни одного, кто становился всего раз")
    void testPlayersBestOnceEmpty(){
        List<Match> matches = List.of();

        long expected = 0;

        long actual = PlayerService.findPlayersBestOnce(matches);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Количество игроков, которые становились лучшими всего один раз, " +
            "если нет ни одного, кто становился всего раз")
    void testPlayersBestOnceNull(){
        List<Match> matches = List.of(
                new Match("Filatov", "Anikandrov"),
                new Match("Filatov", "Kostrikin"),
                new Match("Anikandrov", "Tupikov"),
                new Match("Filatov", "Kostrikin"),
                new Match("Tupikov", "Kostrikin"));

        long expected = 0;

        long actual = PlayerService.findPlayersBestOnce(matches);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Количество игроков, которые становились лучшими всего один раз, " +
            "если таких несколько")
    void testPlayersBestOnceMany(){
        List<Match> matches = List.of(
                new Match("Filatov", "Anikandrov"),
                new Match("Filatov", "Kostrikin"),
                new Match("Filin", "Tupikov"),
                new Match("Filatov", "Antipov"),
                new Match("Gavrilov", "Kostrikin"));

        long expected = 5;

        long actual = PlayerService.findPlayersBestOnce(matches);

        assertEquals(expected, actual);
    }
}
