package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.SongPlayback;
import ru.vsu.amm.java.enums.Genre;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SongGenerator {
    private static final int DATA_AMOUNT = 5;
    private static final int USER_AMOUNT = 4;
    private static final int START_YEAR = 2020;

    private static final String[] USERNAMES = new String[USER_AMOUNT];
    private static final String[] TITLES = new String[DATA_AMOUNT];
    private static final String[] ARTISTS = new String[DATA_AMOUNT];

    private static final Genre[] GENRES = Genre.values();

    public SongGenerator() {
        for (int i = 1; i <= USER_AMOUNT; ++i) {
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

        int year = randBetween(START_YEAR, LocalDate.now().getYear());
        int month = randBetween(1, 12);
        LocalDate date = LocalDate.of(year, month, 1);
        int day = randBetween(1, date.getMonth().length(date.isLeapYear()));
        return LocalDate.of(year, month, day);
    }

    public SongPlayback generateOneSong() {
        Random rand = new Random();
        String title = TITLES[rand.nextInt(DATA_AMOUNT)];
        String artist = ARTISTS[rand.nextInt(DATA_AMOUNT)];
        Genre genre = GENRES[rand.nextInt(GENRES.length)];
        LocalDate date = generateDate();
        return new SongPlayback(title, artist,
                genre, date);
    }

    public Map<String, List<SongPlayback>> generateUserData() {
        var result = new HashMap<String, List<SongPlayback>>();
        for (int i = 0; i < USER_AMOUNT; ++i) {
            result.put(USERNAMES[i], IntStream.range(0, DATA_AMOUNT)
                    .mapToObj(x -> generateOneSong())
                    .toList());
        }
        return result;
    }
}
