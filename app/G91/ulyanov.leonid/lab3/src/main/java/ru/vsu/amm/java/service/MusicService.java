package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.SongPlayback;
import ru.vsu.amm.java.enums.Genre;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MusicService {
    public static int getAmountOfRecentStreams(Map<String, List<SongPlayback>> songs,
                                               String title, String artist, Genre genre) {
        var firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        return songs.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .filter(song -> (song.listeningDate().isAfter(firstDayOfMonth)
                                        || song.listeningDate().isEqual(firstDayOfMonth))
                                        && song.title().equals(title)
                                        && song.artist().equals(artist)
                                        && song.genre().equals(genre))
                                .distinct()
                ))
                .entrySet().stream()
                .flatMapToInt(x -> IntStream.of((int) x.getValue().count())).sum();
    }

    public static Map<String, Set<String>> getUserStreamingData(Map<String, List<SongPlayback>> songs) {
        return songs.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(song -> song.title()
                                        + " by " + song.artist()
                                        + ", " + song.genre())
                                .collect(Collectors.toSet())));
    }

    public static Map<String, Set<String>> getUserStreamingDataLeastPopular(Map<String, List<SongPlayback>> songs) {
        var date = LocalDate.now().withMonth(LocalDate.now().getMonthValue() - 2);
        var recentSongsMap = songs.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .filter(song -> song.listeningDate().isAfter(date)
                                        || song.listeningDate().isEqual(date))
                                .map(song -> song.title()
                                        + " by " + song.artist()
                                        + ", " + song.genre())
                                .collect(Collectors.toSet())));

        // нужно убрать дубликаты песен, которые могли слушаться недавно и раньше 3 месяцев
        return songs.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(song -> song.title()
                                        + " by " + song.artist()
                                        + ", " + song.genre())
                                .filter(song -> !recentSongsMap.get(entry.getKey()).contains(song))
                                .collect(Collectors.toSet())));
    }

    public static Map<String, String> getUserStreamingDataMostFavorite(Map<String, List<SongPlayback>> songs) {
        return songs.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .collect(Collectors.groupingBy(song -> song.title()
                                                + " by " + song.artist()
                                                + ", " + song.genre(),
                                        Collectors.counting()))
                                .entrySet().stream()
                                .max(Map.Entry.comparingByValue())
                                .map(Map.Entry::getKey)
                                .orElse("None")));
    }
}
