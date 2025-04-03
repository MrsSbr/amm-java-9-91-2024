package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.config.DatabaseConfiguration;
import ru.vsu.amm.java.entities.Note;
import ru.vsu.amm.java.entities.UserEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NoteRepository implements Repository<Note> {
    private final DataSource dataSource;

    public NoteRepository() {
        this.dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<Note> findById(Long id) throws SQLException {
        final String query = "SELECT noteId, content, createdAt, updatedAt, userId, categoryId FROM Note WHERE noteId = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new Note(
                        resultSet.getLong("noteId"),
                        resultSet.getString("content"),
                        resultSet.getTimestamp("createdAt").toLocalDateTime(),
                        resultSet.getTimestamp("updatedAt").toLocalDateTime(),
                        resultSet.getLong("userId"),
                        resultSet.getLong("categoryId")
                ));
            }
            return Optional.empty();
        }
    }

    @Override
    public List<Note> findAll() throws SQLException {
        final String query = "SELECT noteId, content, createdAt, updatedAt, userId, categoryId FROM Note";
        List<Note> notes = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {


            while (resultSet.next()) {
                notes.add(new Note(
                        resultSet.getLong("noteId"),
                        resultSet.getString("content"),
                        resultSet.getTimestamp("createdAt").toLocalDateTime(),
                        resultSet.getTimestamp("updatedAt").toLocalDateTime(),
                        resultSet.getLong("userId"),
                        resultSet.getLong("categoryId")
                ));
            }
        }

        return notes;
    }

    public List<Note> findByUserId(Long userId) throws SQLException {
        final String query = "SELECT noteId, content, createdAt, updatedAt, userId, categoryId FROM Note WHERE userId = ?";
        List<Note> notes = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            preparedStatement.setLong(1, userId);

            while (resultSet.next()) {
                notes.add(new Note(
                        resultSet.getLong("noteId"),
                        resultSet.getString("content"),
                        resultSet.getTimestamp("createdAt").toLocalDateTime(),
                        resultSet.getTimestamp("updatedAt").toLocalDateTime(),
                        resultSet.getLong("userId"),
                        resultSet.getLong("categoryId")
                ));
            }
        }

        return notes;
    }

    @Override
    public void save(Note note) throws SQLException {
        final String query = "INSERT INTO Note (content, createdAt, updatedAt, userId, categoryId) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, note.getContent());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(note.getCreatedAt()));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(note.getUpdatedAt()));
            preparedStatement.setLong(4, note.getUserId());
            preparedStatement.setLong(5, note.getCategoryId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Note note) throws SQLException {
        final String query = "DELETE FROM Note WHERE noteId = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, note.getNoteId());
            preparedStatement.executeUpdate();
        }
    }

    public List<Note> findByCategory(Long categoryId) throws SQLException {
        final String query = "SELECT noteId, content, createdAt, updatedAt, userId, categoryId FROM Note WHERE categoryId = ?";
        List<Note> notes = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                notes.add(new Note(
                        resultSet.getLong("noteId"),
                        resultSet.getString("content"),
                        resultSet.getTimestamp("createdAt").toLocalDateTime(),
                        resultSet.getTimestamp("updatedAt").toLocalDateTime(),
                        resultSet.getLong("userId"),
                        resultSet.getLong("categoryId")
                ));
            }
        }
        return notes;
    }
}