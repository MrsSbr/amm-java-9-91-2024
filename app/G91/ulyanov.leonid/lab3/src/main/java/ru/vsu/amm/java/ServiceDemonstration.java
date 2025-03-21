package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.SongPlayback;
import ru.vsu.amm.java.enums.Genre;
import ru.vsu.amm.java.service.MusicService;
import ru.vsu.amm.java.util.SongGenerator;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ServiceDemonstration {
    public static void main(String[] args) {
        SongGenerator gen = new SongGenerator();
        Map<String, List<SongPlayback>> songsCollection = gen.generateUserData();

        System.out.println("Amount of times when song 'title2 by artist1, ROCK' was listened this month: "
                + MusicService.getAmountOfRecentStreams(songsCollection,
                "title2", "artist1", Genre.ROCK));
        System.out.println();

        System.out.println("All streaming data");
        iterateThroughSongs(MusicService.getUserStreamingData(songsCollection));
        System.out.println();

        System.out.println("Streaming data for least popular songs");
        iterateThroughSongs(MusicService.getUserStreamingDataLeastPopular(songsCollection));
        System.out.println();

        System.out.println("Streaming data for user favorite songs");
        var answer = MusicService.getUserStreamingDataMostFavorite(songsCollection);
        for (var entry : answer.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
    }

    private static void iterateThroughSongs(Map<String, Set<String>> result) {
        for (var entry : result.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
    }
}