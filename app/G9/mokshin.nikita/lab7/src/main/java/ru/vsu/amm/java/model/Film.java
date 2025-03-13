package ru.vsu.amm.java.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Film {
    private Long id;
    private String title;
    private String slogan;
    private String description;
    private LocalDate releaseDate;
    private Genre genre;
    private User author;
}
