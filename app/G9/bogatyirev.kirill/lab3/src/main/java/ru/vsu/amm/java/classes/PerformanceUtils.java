package ru.vsu.amm.java.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PerformanceUtils {
    private List<Plays> plays = new ArrayList<>();

    public PerformanceUtils() {
    }

    public PerformanceUtils(List<Plays> plays) {
        this.plays = plays;
    }

    public List<String> findPopular() {
        plays.sort((play1, play2) -> Integer.compare(play2.getCountTickets(), play1.getCountTickets()));

        int max = plays.getFirst().getCountTickets();

        return plays.stream()
                .filter(play -> play.getCountTickets() == max)
                .map(Plays::getName)
                .toList();
    }

    public List<String> notNullPlays() {
        return plays.stream()
                .filter(play -> play.getCountTickets() > 0)
                .map(Plays::getName)
                .toList();
    }
}
