package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.Genre;

import java.time.LocalDate;

public record SongPlayback(String username, String title, String artist,
                           Genre genre, int durationInSecs,
                           LocalDate listeningDate) {
}