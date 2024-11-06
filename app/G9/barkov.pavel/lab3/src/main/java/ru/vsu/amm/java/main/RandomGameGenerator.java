package ru.vsu.amm.java.main;

import java.time.LocalDate;
import java.util.*;

public class RandomGameGenerator {
    public static final Random random = new Random();

    private RandomGameGenerator() {
    }

    public static GameRecord Generate() {
        String[] genres = new String[]{"warGame", "logical", "card", "rpg", "euroGame"};
        LocalDate data = LocalDate.of(random.nextInt(124) + 1900,
                random.nextInt(11),
                random.nextInt(28));
        Integer part = random.nextInt(12);
        Genre genre = Genre.valueOf(genres[random.nextInt(5)]);
        return new GameRecord(data, genre.name() + "Game" + part, genre,
                random.nextInt(5000));
    }
}
