package ru.vsu.amm.java.main;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class RandomGameGenerator {
    public static final Random random = new Random();

    private RandomGameGenerator() {
    }

    public static GameRecord Generate() {
        final List<Genre> genres = List.of(Genre.warGame, Genre.logical, Genre.card, Genre.rpg, Genre.euroGame);
        LocalDate data = LocalDate.of(random.nextInt(124) + 1900,
                random.nextInt(11),
                random.nextInt(28));
        Integer part = random.nextInt(12);
        Genre genre = genres.get(random.nextInt(5));
        return new GameRecord(data, genre.name() + "Game" + part,
                genre, random.nextInt(5000));
    }
}
