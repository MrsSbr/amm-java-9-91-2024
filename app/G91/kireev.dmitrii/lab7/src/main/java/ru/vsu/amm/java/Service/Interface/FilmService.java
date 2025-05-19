package ru.vsu.amm.java.Service.Interface;

import ru.vsu.amm.java.Model.Entity.FilmEntity;

import java.util.List;
import java.util.Optional;

public interface FilmService {

    Optional<FilmEntity> getFilmById(Long id);

    List<FilmEntity> getAllFilms();

    void addFilm(FilmEntity film);

    void updateFilm(FilmEntity film);

    void deleteFilm(Long id);
}
