package ru.vsu.amm.java.repos;

import lombok.extern.slf4j.Slf4j;
import ru.vsu.amm.java.entities.BookUpdate;
import ru.vsu.amm.java.mappers.BookUpdatesMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.vsu.amm.java.config.DbConfig.getDataSource;

@Slf4j
public class BookUpdatesRepository implements Repository<BookUpdate> {
    private final DataSource dataSource;

    public BookUpdatesRepository() {
        this.dataSource = getDataSource();
    }

    @Override
    public Optional<BookUpdate> getById(int id) throws SQLException {
        String query = """
                SELECT Id_book, Id_user, UpdateTime, UpdateType
                FROM BookUpdates
                WHERE Id_update = ?;
                """;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            BookUpdatesMapper bookUpdatesMapper = new BookUpdatesMapper();

            if (resultSet.next()) {
                return Optional.of(bookUpdatesMapper.mapRowToObject(resultSet));
            }

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }

        return Optional.empty();
    }

    @Override
    public List<BookUpdate> getAll() {
        String query = """
                SELECT Id_book, Id_user, UpdateTime, UpdateType
                FROM BookUpdates;
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet resultSet = ps.executeQuery();
            BookUpdatesMapper bookUpdatesMapper = new BookUpdatesMapper();

            List<BookUpdate> bookUpdates = new ArrayList<>();

            while (resultSet.next()) {
                bookUpdates.add(bookUpdatesMapper.mapRowToObject(resultSet));
            }

            return bookUpdates;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    @Override
    public Integer create(BookUpdate bookUpdate) {
        String query = """
                INSERT INTO BookUpdates(Id_book, Id_user, UpdateTime, UpdateType)
                VALUES (?, ?, ?, ?);
                """;

        try (Connection connection = dataSource.getConnection()) {

            BookUpdatesMapper bookUpdatesMapper = new BookUpdatesMapper();
            PreparedStatement ps = bookUpdatesMapper.mapObjectToRow(bookUpdate, connection, query);
            ps.execute();

            ResultSet resultSet = ps.getGeneratedKeys();
            return resultSet.getInt(1);

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public void update(BookUpdate bookUpdate) {
        String query = """
                UPDATE BookUpdates
                SET Id_book = ?, Id_user = ?, UpdateTime = ?, UpdateType = ?
                    WHERE Id_update = ?;
                """;

        try (Connection connection = dataSource.getConnection()) {

            BookUpdatesMapper bookUpdatesMapper = new BookUpdatesMapper();
            PreparedStatement ps = bookUpdatesMapper.mapObjectToRow(bookUpdate, connection, query);
            ps.setInt(1, bookUpdate.getUpdateId());
            ps.execute();

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void delete(BookUpdate bookUpdate) {
        String query = """
                DELETE FROM BookUpdates
                WHERE id_update = ?;
                """;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, bookUpdate.getUpdateId());
            ps.execute();

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}
