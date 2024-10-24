package ru.vsu.amm.java;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.GameWalkthrough;
import ru.vsu.amm.java.enums.Genre;
import ru.vsu.amm.java.enums.Rating;
import ru.vsu.amm.java.service.GameWalkthroughStatsService;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public final class GameWalkthroughTests {

    private static final List<GameWalkthrough> nullList = null;
    private static final List<GameWalkthrough> emptyList = new ArrayList<>();
    private static final List<GameWalkthrough> fullList = getSomeWalkthroughes();

    @Test
    public void testFindHighestRatedGenreWithNull() {
        assertThrows(
                NullPointerException.class,
                () -> GameWalkthroughStatsService.findHighestRatedGenre(nullList)
        );
    }

    @Test
    public void testFindHighestRatedGenreWithEmptyList() {
        assertThrows(
                NullPointerException.class,
                () -> GameWalkthroughStatsService.findHighestRatedGenre(emptyList)
        );
    }

    @Test
    public void testFindHighestRatedGenreWithFullList() {
        assertEquals(
                Genre.Adventure,
                GameWalkthroughStatsService.findHighestRatedGenre(fullList)
        );
    }

    @Test
    public void testFindMonthWithMostPlaytimeWithNull() {
        assertThrows(
                NullPointerException.class,
                () -> GameWalkthroughStatsService.findMonthWithMostPlaytime(nullList)
        );
    }

    @Test
    public void testFindMonthWithMostPlaytimeWithEmptyList() {
        assertThrows(
                NullPointerException.class,
                () -> GameWalkthroughStatsService.findMonthWithMostPlaytime(emptyList)
        );
    }

    @Test
    public void testFindMonthWithMostPlaytimeWithFullList() {
        assertEquals(
                YearMonth.of(2020, 8),
                GameWalkthroughStatsService.findMonthWithMostPlaytime(fullList)
        );
    }

    @Test
    public void testFindAllMultipleWalkthroughGamesWithNull() {
        assertThrows(
                NullPointerException.class,
                () -> GameWalkthroughStatsService.findAllMultipleWalkthroughGames(nullList)
        );
    }

    @Test
    public void testFindAllMultipleWalkthroughGamesWithEmptyList() {
        assertEquals(
                new ArrayList<>(),
                GameWalkthroughStatsService.findAllMultipleWalkthroughGames(emptyList)
        );
    }

    @Test
    public void testFindAllMultipleWalkthroughGamesWithFullList() {
        assertEquals(
                List.of("The Walking Dead"),
                GameWalkthroughStatsService.findAllMultipleWalkthroughGames(fullList)
        );
    }

    private static List<GameWalkthrough> getSomeWalkthroughes() {
        return new ArrayList<>(List.of(
                new GameWalkthrough("The Witcher 3", Genre.RPG, LocalDate.of(2020, 6, 1), 50000, Rating.five),
                new GameWalkthrough("Portal 2", Genre.Adventure, LocalDate.of(2020, 7, 22), 5000, Rating.five),
                new GameWalkthrough("Half-Life 2", Genre.FPS, LocalDate.of(2020, 7, 22), 10000, Rating.four),
                new GameWalkthrough("The Walking Dead", Genre.Adventure, LocalDate.of(2020, 8, 5), 20000, Rating.five),
                new GameWalkthrough("Fallout 4", Genre.RPG, LocalDate.of(2020, 8, 15), 50000, Rating.four),
                new GameWalkthrough("The Walking Dead", Genre.Adventure, LocalDate.of(2020, 9, 11), 20000, Rating.five)
        ));
    }
}
