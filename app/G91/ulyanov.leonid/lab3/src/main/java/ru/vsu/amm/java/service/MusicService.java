package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.SongPlayback;
import ru.vsu.amm.java.enums.Genre;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MusicService {
    public static int getAmountOfRecentStreams(List<SongPlayback> playbacks,
                                               String title, String artist, Genre genre) {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        return playbacks.stream()
                .filter(song -> song.listeningDate().getYear() == currentYear
                        && song.listeningDate().getMonthValue() == currentMonth
                        && song.title().equals(title)
                        && song.artist().equals(artist)
                        && song.genre().equals(genre))
                .collect(Collectors.toSet())
                .size();
    }

    public static Map<String, Set<String>> getUserStreamingData(List<SongPlayback> songs) {
        return songs.stream()
                .collect(Collectors.groupingBy(SongPlayback::username,
                        Collectors.mapping(song -> song.title()
                                        + " by " + song.artist()
                                        + ", " + song.genre(),
                                Collectors.toSet())));
    }

    public static Map<String, Set<String>> getUserStreamingDataLeastPopular(List<SongPlayback> songs) {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        // нужно убрать дубликаты песен, которые могли слушаться недавно и раньше 3 месяцев
        return songs.stream()
                .filter(song -> (song.listeningDate().getYear() == currentYear
                        && song.listeningDate().getMonthValue() <= currentMonth - 3)
                        || song.listeningDate().getYear() < currentYear)
                .collect(Collectors.groupingBy(SongPlayback::username,
                        Collectors.mapping(song -> song.title()
                                        + " by " + song.artist()
                                        + ", " + song.genre(),
                                Collectors.toSet())));
    }

    public static Map<String, String> getUserStreamingDataMostFavorite(List<SongPlayback> songs) {
        Map<String, List<String>> streamingData = songs.stream()
                .collect(Collectors.groupingBy(SongPlayback::username,
                        Collectors.mapping(song -> song.title()
                                        + " by " + song.artist()
                                        + ", " + song.genre(),
                                Collectors.toList())));
        return streamingData.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .collect(Collectors.groupingBy(song -> song,
                                        Collectors.counting()))
                                .entrySet().stream()
                                .max(Map.Entry.comparingByValue())
                                .map(Map.Entry::getKey)
                                .orElse("No favorite song")));
    }
}
