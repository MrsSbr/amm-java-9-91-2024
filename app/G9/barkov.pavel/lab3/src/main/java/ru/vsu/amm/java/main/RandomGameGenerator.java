package ru.vsu.amm.java.main;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class RandomGameGenerator {
    public static final Random RANDOM = new Random();
    private static final List<Genre> GENRES = List.of(Genre.warGame, Genre.logical, Genre.card, Genre.rpg, Genre.euroGame);
    private static final int MIN_YEAR = 1900;
    private static final int BOUND_YEAR = 124;
    private static final int COUNT_MONTHS = 12;
    private static final int COUNT_DAYS = 28;
    private static final int MAX_PART = 12;
    private static final int MAX_PRICE = 5000;
    private RandomGameGenerator() {
    }

    public static GameRecord Generate() {
        LocalDate data = LocalDate.of(RANDOM.nextInt(BOUND_YEAR) + MIN_YEAR,
                RANDOM.nextInt(COUNT_MONTHS),
                RANDOM.nextInt(COUNT_DAYS));
        int part = RANDOM.nextInt(MAX_PART);
        Genre genre = GENRES.get(RANDOM.nextInt());
        return new GameRecord(data, genre.name() + "Game" + part,
                genre, RANDOM.nextInt(MAX_PRICE));
    }
}
