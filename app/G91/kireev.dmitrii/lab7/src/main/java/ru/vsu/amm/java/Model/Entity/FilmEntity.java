package ru.vsu.amm.java.Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmEntity {

    private Long filmId;
    private String name;
    private String genre;
    private Integer duration;
    private String screenWriter;
    private double rating;

}
