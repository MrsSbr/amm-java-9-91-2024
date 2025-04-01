package ru.vsu.amm.java.repos;

import ru.vsu.amm.java.entities.BookUpdates;
import ru.vsu.amm.java.mappers.BookUpdatesMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookUpdatesRepository implements Repository<BookUpdates> {
    private final DataSource dataSource;

    public BookUpdatesRepository(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<BookUpdates> getById(int id) throws SQLException {
        String query = """
                SELECT *
                FROM BookUpdates WHERE Id_update = ?;
                """;

        Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet resultSet = ps.executeQuery();
        BookUpdatesMapper bookUpdatesMapper = new BookUpdatesMapper();

        if (resultSet.next()) {
            return Optional.of(bookUpdatesMapper.mapRowToObject(resultSet));
        }

        return Optional.empty();
    }

    @Override
    public List<BookUpdates> getAll() throws SQLException {
        String query = """
                SELECT *
                FROM BookUpdates;
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet resultSet = ps.executeQuery();
            BookUpdatesMapper bookUpdatesMapper = new BookUpdatesMapper();

            List<BookUpdates> bookUpdates = new ArrayList<>();

            while (resultSet.next()) {
                bookUpdates.add(bookUpdatesMapper.mapRowToObject(resultSet));
            }

            return bookUpdates;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void create(BookUpdates bookUpdates) throws SQLException {
        String query = """
                INSERT INTO BookUpdates(BookId, UserId, UpdateTime, UpdateType)
                VALUES (?, ?, ?, ?);
                """;

        try (Connection connection = dataSource.getConnection()) {

            BookUpdatesMapper bookUpdatesMapper = new BookUpdatesMapper();
            PreparedStatement ps = bookUpdatesMapper.mapObjectToRow(bookUpdates, connection, query);
            ps.execute();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void update(BookUpdates bookUpdates) throws SQLException {
        String query = """
                UPDATE BookUpdates
                SET BookId = ?, UserId = ?, UpdateTime = ?, UpdateType = ?
                    WHERE Id_update = ?;
                """;

        try (Connection connection = dataSource.getConnection()) {

            BookUpdatesMapper bookUpdatesMapper = new BookUpdatesMapper();
            PreparedStatement ps = bookUpdatesMapper.mapObjectToRow(bookUpdates, connection, query);
            ps.setInt(1, bookUpdates.getUpdateId());
            ps.execute();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void delete(BookUpdates bookUpdates) throws SQLException {
        String query = """
                DELETE FROM BookUpdates
                WHERE id_update = ?;
                """;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, bookUpdates.getUpdateId());
            ps.execute();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
}
