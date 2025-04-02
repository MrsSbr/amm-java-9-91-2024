package ru.vsu.amm.java.Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class FilmEntity {

    private Long filmId;
    private String name;
    private String genre;
    private Duration duration;
    private String screenWriter;
    private double rating;

}
