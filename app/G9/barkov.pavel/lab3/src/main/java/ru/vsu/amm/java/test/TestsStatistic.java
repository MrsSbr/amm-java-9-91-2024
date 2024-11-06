package ru.vsu.amm.java.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.main.GameRecord;
import ru.vsu.amm.java.main.Genre;
import ru.vsu.amm.java.main.Statistic;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestsStatistic {
    private static ArrayList<GameRecord> list;

    @BeforeAll
    public static void initList() {
        list = new ArrayList<>(List.of(
                new GameRecord(LocalDate.of(2024, Month.NOVEMBER, 1), "Durak", Genre.card, 50),
                new GameRecord(LocalDate.of(2024, Month.SEPTEMBER, 3), "Poker", Genre.card, 70),
                new GameRecord(LocalDate.of(2000, Month.MAY, 1), "Monopoly", Genre.euroGame, 1550),
                new GameRecord(LocalDate.of(2024, Month.OCTOBER, 31), "D&D", Genre.rpg, 5000),
                new GameRecord(LocalDate.of(2024, Month.SEPTEMBER, 11), "Plane", Genre.warGame, 5000)
        ));
    }

    @Test
    public void testBestSellingGenreGames() {
        List<String> res = Statistic.bestSellingGenreGames(list);
        assertNotNull(res);
        assertTrue(res.contains("Durak"));
        assertTrue(res.contains("Poker"));
        assertFalse(res.contains("Monopoly"));
        assertFalse(res.contains("D&D"));
        assertFalse(res.contains("Plane"));
    }

    @Test
    public void testNameGame() {
        String res = Statistic.nameGame(list);
        assertEquals(res, "Monopoly");
    }

    @Test
    public void testMostSuccessMonth() {
        int res = Statistic.mostSuccessMonth(list);
        assertEquals(res, 9);
    }
}
