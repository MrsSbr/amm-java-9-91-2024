package ru.vsu.amm.java.Entity;

import lombok.Data;

import java.time.Duration;
import java.time.OffsetDateTime;

@Data
public class FilmEntity {

    private long filmId;
    private String name;
    private String genre;
    private Duration duration;
    private String screenWriter;
    private double rating;

    public FilmEntity(String name, String genre, Duration duration, String screenWriter, double rating) {
        this.name = name;
        this.genre = genre;
        this.duration = duration;
        this.screenWriter = screenWriter;
        this.rating = rating;
    }
}
