package ru.vsu.amm.java.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

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
    private Optional<Genre> genreOptional;
    private Optional<User> authorOptional;

    public Film(String title, String slogan, String description, LocalDate releaseDate, Genre genre, User author) {
        this.title = title;
        this.slogan = slogan;
        this.description = description;
        this.releaseDate = releaseDate;
        genreOptional = Optional.of(genre);
        authorOptional = Optional.of(author);
    }
}
