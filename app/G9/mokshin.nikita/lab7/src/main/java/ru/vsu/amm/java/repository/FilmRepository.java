package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.exception.SqlException;
import ru.vsu.amm.java.model.Film;
import ru.vsu.amm.java.model.Genre;
import ru.vsu.amm.java.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FilmRepository implements CrudRepository<Film> {
    private final DataSource dataSource;
    private final GenreRepository genreRepository;
    private final UserRepository userRepository;

    public FilmRepository(final DataSource dataSource) {
        this.dataSource = dataSource;
        genreRepository = new GenreRepository(dataSource);
        userRepository = new UserRepository(dataSource);
    }

    @Override
    public Optional<Film> findById(long id) {
        String sql = "SELECT id, title, slogan, description, release_date, id_genre, id_user FROM film WHERE id = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Film film = parseResultSet(resultSet);
                return Optional.of(film);
            }

        } catch (SQLException e) {
            throw new SqlException(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Film> findAll() {
        List<Film> films = new ArrayList<>();
        String sql = "SELECT id, title, slogan, description, release_date, id_genre, id_user FROM film";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Film film = parseResultSet(resultSet);
                films.add(film);
            }

        } catch (SQLException e) {
            throw new SqlException(e.getMessage());
        }
        return films;
    }

    @Override
    public void save(Film film) {
        String sql = film.getId() == null ?
                "INSERT INTO film (title, slogan, description, release_date, id_genre, id_user) VALUES (?, ?, ?, ?, ?, ?)" :
                "UPDATE film SET title = ?, slogan = ?, description = ?, release_date = ?, id_genre = ?, id_user = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, film.getTitle());
            statement.setString(2, film.getSlogan());
            statement.setString(3, film.getDescription());
            statement.setDate(4, Date.valueOf(film.getReleaseDate()));
            statement.setLong(5, film.getGenreOptional().get().getId());
            statement.setLong(6, film.getAuthorOptional().get().getId());

            if (film.getId() == null) {
                statement.executeUpdate();
            } else {
                statement.setLong(7, film.getId());
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new SqlException(e.getMessage());
        }
    }

    @Override
    public void delete(Film film) {
        String sql = "DELETE FROM film WHERE id = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, film.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new SqlException(e.getMessage());
        }
    }

    private Film parseResultSet(ResultSet resultSet) throws SQLException {
        Film film = new Film();
        film.setId(resultSet.getLong("id"));
        film.setTitle(resultSet.getString("title"));
        film.setSlogan(resultSet.getString("slogan"));
        film.setDescription(resultSet.getString("description"));
        film.setReleaseDate(resultSet.getDate("release_date").toLocalDate());

        long genreId = resultSet.getLong("id_genre");
        long authorId = resultSet.getLong("id_user");

        Optional<Genre> genre = genreRepository.findById(genreId);
        Optional<User> author = userRepository.findById(authorId);

        film.setGenreOptional(genre);
        film.setAuthorOptional(author);
        return film;
    }
}
