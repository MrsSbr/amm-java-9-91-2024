package ru.vsu.amm.java.Service.Impl;

import ru.vsu.amm.java.Config.DatabaseConfig;
import ru.vsu.amm.java.Exception.DbException;
import ru.vsu.amm.java.Mapper.Impl.FilmMapper;
import ru.vsu.amm.java.Model.Entity.FilmEntity;
import ru.vsu.amm.java.Repository.Impl.FilmRepo;
import ru.vsu.amm.java.Service.Interface.FilmService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class DefaultFilmServiceImpl implements FilmService {

    private static final String DB_ERROR = "Ошибка бд";

    private static final Logger log = Logger.getLogger(DefaultFilmServiceImpl.class.getName());

    private final FilmRepo filmRepo;

    public DefaultFilmServiceImpl() {
        this.filmRepo = new FilmRepo(DatabaseConfig.getDataSource(), new FilmMapper());
    }

    @Override
    public Optional<FilmEntity> getFilmById(Long id) {
        log.info("Попытка получения фильма с ID: " + id);
        try {
            Optional<FilmEntity> film = filmRepo.findById(id);
            if (film.isPresent()) {
                log.info("Фильм с ID " + id + " найден.");
            } else {
                log.warning("Фильм с ID " + id + " не найден.");
            }
            return film;
        } catch (SQLException e) {
            log.severe("Ошибка при получении фильма с ID " + id + ": " + e.getMessage());
            throw new DbException(DB_ERROR);
        }
    }

    @Override
    public List<FilmEntity> getAllFilms() {
        log.info("Попытка получения списка всех фильмов.");
        try {
            List<FilmEntity> films = filmRepo.findAll();
            log.info("Список всех фильмов получен. Количество фильмов: " + films.size());
            return films;
        } catch (SQLException e) {
            log.severe("Ошибка при получении списка фильмов: " + e.getMessage());
            throw new DbException(DB_ERROR);
        }
    }

    @Override
    public void addFilm(FilmEntity film) {
        log.info("Попытка добавления фильма: " + film.getName());
        try {
            filmRepo.save(film);
            log.info("Фильм " + film.getName() + " успешно добавлен.");
        } catch (SQLException e) {
            log.severe("Ошибка при добавлении фильма " + film.getName() + ": " + e.getMessage());
            throw new DbException(DB_ERROR);
        }
    }

    @Override
    public void updateFilm(FilmEntity film) {
        log.info("Попытка обновления фильма с ID: " + film.getFilmId());
        try {
            filmRepo.update(film);
            log.info("Фильм с ID " + film.getFilmId() + " успешно обновлен.");
        } catch (SQLException e) {
            log.severe("Ошибка при обновлении фильма с ID " + film.getFilmId() + ": " + e.getMessage());
            throw new DbException(DB_ERROR);
        }
    }

    @Override
    public void deleteFilm(Long id) {
        log.info("Попытка удаления фильма с ID: " + id);
        try {
            filmRepo.delete(id);
            log.info("Фильм с ID " + id + " успешно удален.");
        } catch (SQLException e) {
            log.severe("Ошибка при удалении фильма с ID " + id + ": " + e.getMessage());
            throw new DbException(DB_ERROR);
        }
    }
}