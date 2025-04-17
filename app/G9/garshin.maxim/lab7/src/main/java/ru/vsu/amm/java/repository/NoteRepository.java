package ru.vsu.amm.java.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.config.DatabaseConfiguration;
import ru.vsu.amm.java.entities.Note;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NoteRepository implements Repository<Note> {
    private static final Logger logger = LoggerFactory.getLogger(NoteRepository.class);
    private final DataSource dataSource;

    public NoteRepository() {
        this.dataSource = DatabaseConfiguration.getDataSource();
    }

    private Note mapResultSetToNote(ResultSet resultSet) throws SQLException {
        Time createdAtTime = resultSet.getTime("createdAt");
        Time updatedAtTime = resultSet.getTime("updatedAt");
        LocalDate currentDate = LocalDate.now();

        LocalDateTime createdAt = (createdAtTime != null) ?
                LocalDateTime.of(currentDate, createdAtTime.toLocalTime()) : null;
        LocalDateTime updatedAt = (updatedAtTime != null) ?
                LocalDateTime.of(currentDate, updatedAtTime.toLocalTime()) : null;

        return new Note(
                resultSet.getLong("noteId"),
                resultSet.getString("content"),
                createdAt,
                updatedAt,
                resultSet.getLong("userId"),
                resultSet.getLong("categoryId")
        );
    }

    private List<Note> executeQueryWithSingleParam(String query, Long param, String paramName) throws SQLException {
        logger.info("Executing query: {} with {}: {}", query, paramName, param);
        List<Note> notes = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, param);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Note note = mapResultSetToNote(resultSet);
                notes.add(note);
                logger.info("Note retrieved: {}", note);
            }
        }

        logger.info("Total notes found: {}", notes.size());
        return notes;
    }

    @Override
    public Optional<Note> findById(Long id) throws SQLException {
        final String query = "SELECT noteId, content, createdAt, updatedAt, userId, categoryId FROM Note WHERE noteId = ?";
        logger.info("Executing query: {} with id: {}", query, id);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Note note = mapResultSetToNote(resultSet);
                logger.info("Note found: {}", note);
                return Optional.of(note);
            }

            logger.info("No note found with id: {}", id);
            return Optional.empty();
        }
    }

    @Override
    public List<Note> findAll() throws SQLException {
        final String query = "SELECT noteId, content, createdAt, updatedAt, userId, categoryId FROM Note";
        logger.info("Executing query: {}", query);

        List<Note> notes = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Note note = mapResultSetToNote(resultSet);
                notes.add(note);
                logger.info("Note retrieved: {}", note);
            }
        }

        logger.info("Total notes found: {}", notes.size());
        return notes;
    }

    public List<Note> findByUserId(Long userId) throws SQLException {
        final String query = "SELECT noteId, content, createdAt, updatedAt, userId, categoryId FROM Note WHERE userId = ?";
        return executeQueryWithSingleParam(query, userId, "userId");
    }

    @Override
    public void save(Note note) throws SQLException {
        final String query = "INSERT INTO Note (content, createdAt, updatedAt, userId, categoryId) VALUES (?, ?, ?, ?, ?)";
        logger.info("Executing query: {} with content: {}, userId: {}, categoryId: {}",
                query, note.getContent(), note.getUserId(), note.getCategoryId());

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, note.getContent());
            preparedStatement.setTime(2, Time.valueOf(note.getCreatedAt().toLocalTime()));
            preparedStatement.setTime(3, Time.valueOf(note.getUpdatedAt().toLocalTime()));
            preparedStatement.setLong(4, note.getUserId());
            preparedStatement.setLong(5, note.getCategoryId());

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    note.setNoteId(generatedKeys.getLong(1));
                }
            }

            logger.info("Note saved with id: {}", note.getNoteId());
        }
    }

    @Override
    public void delete(Note note) throws SQLException {
        final String query = "DELETE FROM Note WHERE noteId = ?";
        logger.info("Executing delete query: {} with noteId: {}", query, note.getNoteId());

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, note.getNoteId());
            int rowsAffected = preparedStatement.executeUpdate();
            logger.info("Deleted note with id: {}. Rows affected: {}", note.getNoteId(), rowsAffected);
        }
    }

    public List<Note> findByUserIdAndCategory(Long userId, Long categoryId) throws SQLException {
        final String query = "SELECT noteId, content, createdAt, updatedAt, userId, categoryId FROM Note WHERE userId = ? AND categoryId = ?";
        logger.info("Executing query: {} with userId: {} and categoryId: {}", query, userId, categoryId);

        List<Note> notes = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Note note = mapResultSetToNote(resultSet);
                notes.add(note);
                logger.info("Note retrieved for user {} and category {}: {}", userId, categoryId, note);
            }
        }

        logger.info("Total notes found for user {} and category {}: {}", userId, categoryId, notes.size());
        return notes;
    }

    public Optional<Note> getNoteById(long noteId, String username) throws SQLException {
        final String query = "SELECT n.noteId, n.content, n.createdAt, n.updatedAt, n.userId, n.categoryId " +
                "FROM Note n JOIN UserEntity u ON n.userId = u.userId " +
                "WHERE n.noteId = ? AND u.username = ?";

        logger.info("Executing getNoteById query: {} with noteId: {}, username: {}", query, noteId, username);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, noteId);
            preparedStatement.setString(2, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Note note = mapResultSetToNote(resultSet);
                logger.info("Note found and belongs to user: {}", note);
                return Optional.of(note);
            }

            logger.warn("Note not found or doesn't belong to user. NoteId: {}, Username: {}", noteId, username);
            return Optional.empty();
        }
    }

    public void updateNoteContent(long noteId, String content, String username) throws SQLException {
        final String query = "UPDATE Note " +
                "SET content = ?, updatedAt = CURRENT_TIMESTAMP " +
                "WHERE noteId = ? AND userId IN (" +
                "    SELECT userId FROM UserEntity WHERE username = ?)";

        logger.info("Executing updateNoteContent query: {} with noteId: {}, username: {}",
                query, noteId, username);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, content);
            preparedStatement.setLong(2, noteId);
            preparedStatement.setString(3, username);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated == 0) {
                logger.error("Failed to update note. Note not found or doesn't belong to user. " +
                        "NoteId: {}, Username: {}", noteId, username);
                throw new SQLException("Note not found or access denied");
            }

            logger.info("Successfully updated note content. NoteId: {}, Rows affected: {}",
                    noteId, rowsUpdated);
        }
    }

    public void deleteNote(long noteId, String username) throws SQLException {
        final String query = "DELETE FROM Note " +
                "WHERE noteId = ? AND userId IN (" +
                "    SELECT userId FROM UserEntity WHERE username = ?)";

        logger.info("Executing deleteNote query: {} with noteId: {}, username: {}",
                query, noteId, username);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, noteId);
            preparedStatement.setString(2, username);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted == 0) {
                logger.error("Failed to delete note. Note not found or doesn't belong to user. " +
                        "NoteId: {}, Username: {}", noteId, username);
                throw new SQLException("Note not found or access denied");
            }

            logger.info("Successfully deleted note. NoteId: {}, Rows affected: {}",
                    noteId, rowsDeleted);
        }
    }
}