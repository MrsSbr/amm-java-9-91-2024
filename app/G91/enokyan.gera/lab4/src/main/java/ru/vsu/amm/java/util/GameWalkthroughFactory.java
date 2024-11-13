package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.GameWalkthrough;
import ru.vsu.amm.java.enums.Genre;
import ru.vsu.amm.java.enums.Rating;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class GameWalkthroughFactory {
    private final static Random rnd = new Random();

    private GameWalkthroughFactory() {
    }

    public static GameWalkthrough generateGameWalkthrough() {
        var name = IntStream.range(0, 3)
                .mapToObj(x -> (char)(rnd.nextInt(26) + 'a'))
                .reduce("", (str, ch) -> str + ch, (x, y) -> y);

        var genreTypes = Genre.values();
        int genreIndex = rnd.nextInt(genreTypes.length);
        Genre genre = genreTypes[genreIndex];

        int year = rnd.nextInt(2010, LocalDate.now().getYear());
        int dayOfYear = rnd.nextInt(1, Year.isLeap(year) ? 367 : 366);
        LocalDate date = LocalDate.ofYearDay(year, dayOfYear);

        int time = rnd.nextInt(60, 57600);

        var ratingTypes = Rating.values();
        int ratingIndex = rnd.nextInt(ratingTypes.length);
        Rating rating = ratingTypes[ratingIndex];

        return new GameWalkthrough(name, genre, date, time, rating);
    }

    public static List<GameWalkthrough> generateGameWalkthrough(int n) {
        return IntStream.range(0, n)
                .mapToObj(x -> generateGameWalkthrough())
                .collect(Collectors.toList());
    }
}
