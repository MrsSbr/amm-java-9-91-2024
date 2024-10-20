package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.SongPlayback;
import ru.vsu.amm.java.enums.Genre;

import java.time.LocalDate;
import java.util.*;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SongGenerator {
    private static final int DATA_AMOUNT = 5;
    private static final int START_YEAR = 2020;
    private static final int MIN_DURATION = 100;
    private static final int MAX_DURATION = 600;

    private static final String[] USERNAMES = new String[DATA_AMOUNT];
    private static final String[] TITLES = new String[DATA_AMOUNT];
    private static final String[] ARTISTS = new String[DATA_AMOUNT];

    private static final Genre[] GENRES = {
            Genre.CLASSICAL,
            Genre.POP,
            Genre.EXPERIMENTAL,
            Genre.INDIE,
            Genre.ROCK
    };

    public SongGenerator() {
        for (int i = 1; i <= DATA_AMOUNT; ++i) {
            USERNAMES[i - 1] = "user" + i;
        }
        for (int i = 1; i <= DATA_AMOUNT; ++i) {
            TITLES[i - 1] = "title" + i;
        }
        for (int i = 1; i <= DATA_AMOUNT; ++i) {
            ARTISTS[i - 1] = "artist" + i;
        }
    }

    private int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    private LocalDate generateDate() {
        GregorianCalendar gc = new GregorianCalendar();

        int year = randBetween(START_YEAR, LocalDate.now().getYear());
        gc.set(Calendar.YEAR, year);

        int month = randBetween(1, 12);
        gc.set(Calendar.MONTH, month);

        int day = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_MONTH));
        gc.set(Calendar.DAY_OF_MONTH, day);

        return LocalDate.of(gc.get(Calendar.YEAR),
                gc.get(Calendar.MONTH) + 1,
                gc.get(Calendar.DAY_OF_MONTH));
    }

    public SongPlayback generateOneSong() {
        Random rand = new Random();

        String username = USERNAMES[rand.nextInt(DATA_AMOUNT)];
        String title = TITLES[rand.nextInt(DATA_AMOUNT)];
        String artist = ARTISTS[rand.nextInt(DATA_AMOUNT)];
        Genre genre = GENRES[rand.nextInt(GENRES.length)];
        int duration = randBetween(MIN_DURATION, MAX_DURATION);
        LocalDate date = generateDate();

        return new SongPlayback(username, title, artist,
                genre, duration, date);
    }

    public List<SongPlayback> GenerateSeveralSongs(int n) {
        return IntStream.range(0, n)
                .mapToObj(x -> generateOneSong())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}