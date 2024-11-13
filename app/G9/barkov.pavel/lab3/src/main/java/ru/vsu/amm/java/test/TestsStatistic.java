package ru.vsu.amm.java.test;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.main.GameRecord;
import ru.vsu.amm.java.main.Genre;
import ru.vsu.amm.java.main.Statistic;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestsStatistic {
    @Test
    public void testBestSellingGenreGames() {
        var list = List.of(
                new GameRecord(LocalDate.of(2004, Month.APRIL, 1), "Durak", Genre.card, 50),
                new GameRecord(LocalDate.of(2024, Month.SEPTEMBER, 3), "Poker", Genre.card, 70),
                new GameRecord(LocalDate.of(2000, Month.MAY, 1), "Monopoly", Genre.euroGame, 1550),
                new GameRecord(LocalDate.of(2024, Month.OCTOBER, 31), "D&D", Genre.rpg, 5000),
                new GameRecord(LocalDate.of(2024, Month.SEPTEMBER, 11), "Plane", Genre.warGame, 5000)
        );
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
        var list = List.of(
                new GameRecord(LocalDate.of(2024, Month.NOVEMBER, 1), "Durak", Genre.card, 50),
                new GameRecord(LocalDate.of(2024, Month.SEPTEMBER, 3), "Poker", Genre.card, 70),
                new GameRecord(LocalDate.of(2000, Month.MAY, 1), "Monopoly", Genre.euroGame, 1550),
                new GameRecord(LocalDate.of(2024, Month.OCTOBER, 31), "D&D", Genre.rpg, 5000),
                new GameRecord(LocalDate.of(2024, Month.SEPTEMBER, 11), "Plane", Genre.warGame, 5000)
        );
        var res = Statistic.nameGame(list);
        assertNotNull(res);
        assertTrue(res.contains("Monopoly"));
        assertFalse(res.size()>1);
    }

    @Test
    public void testMostSuccessMonth() {
        var list = List.of(
                new GameRecord(LocalDate.of(2024, Month.NOVEMBER, 1), "Durak", Genre.card, 50),
                new GameRecord(LocalDate.of(2024, Month.SEPTEMBER, 3), "Poker", Genre.card, 70),
                new GameRecord(LocalDate.of(2000, Month.MAY, 1), "Monopoly", Genre.euroGame, 1550),
                new GameRecord(LocalDate.of(2024, Month.APRIL, 30), "D&D", Genre.rpg, 5000),
                new GameRecord(LocalDate.of(2024, Month.SEPTEMBER, 11), "Plane", Genre.warGame, 5000)
        );
        var res = Statistic.mostSuccessMonth(list);
        assertNotNull(res);
        assertTrue(res.contains(Month.SEPTEMBER));
        assertFalse(res.size()>1);
    }

    @Test
    public void nullTestBestSellingGenreGames() {
        var res = Statistic.bestSellingGenreGames(new ArrayList<GameRecord>());
        assertTrue(res.size()==0);
    }

    @Test
    public void nullTestNameGame() {
        var res = Statistic.nameGame(new ArrayList<GameRecord>());
        assertTrue(res.size()==0);
    }

    @Test
    public void nullTestMostSuccessMonth() {
        var res = Statistic.mostSuccessMonth(new ArrayList<GameRecord>());
        assertTrue(res.size()==0);
    }


    @Test
    public void manyTestNameGame() {
        var list = List.of(
                new GameRecord(LocalDate.of(2024, Month.NOVEMBER, 1), "Durak", Genre.card, 50),
                new GameRecord(LocalDate.of(2024, Month.SEPTEMBER, 3), "Poker", Genre.card, 70),
                new GameRecord(LocalDate.of(2000, Month.MAY, 1), "Monopoly", Genre.euroGame, 1550),
                new GameRecord(LocalDate.of(2024, Month.APRIL, 30), "D&D", Genre.rpg, 5000),
                new GameRecord(LocalDate.of(2024, Month.MARCH, 11), "Plane", Genre.warGame, 5000)
        );
        var res = Statistic.nameGame(list);
        assertTrue(res.contains("Monopoly"));
        assertTrue(res.contains("D&D"));
        assertTrue(res.contains("Plane"));
        assertFalse(res.size()>3);
    }

    @Test
    public void manyTestMostSuccessMonth() {
        var list = List.of(
                new GameRecord(LocalDate.of(2024, Month.NOVEMBER, 1), "Durak", Genre.card, 50),
                new GameRecord(LocalDate.of(2024, Month.SEPTEMBER, 3), "Poker", Genre.card, 70),
                new GameRecord(LocalDate.of(2000, Month.MAY, 1), "Monopoly", Genre.euroGame, 4000),
                new GameRecord(LocalDate.of(2024, Month.APRIL, 30), "D&D", Genre.rpg, 5000),
                new GameRecord(LocalDate.of(2024, Month.FEBRUARY, 11), "Plane", Genre.warGame, 5000)
        );
        var res = Statistic.mostSuccessMonth(list);
        assertTrue(res.contains(Month.APRIL));
        assertTrue(res.contains(Month.FEBRUARY));
        assertFalse(res.size()>2);
    }

}
