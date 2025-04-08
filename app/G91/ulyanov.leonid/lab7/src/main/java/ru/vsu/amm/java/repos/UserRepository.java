package ru.vsu.amm.java.repos;

import lombok.extern.slf4j.Slf4j;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.mappers.UserMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class UserRepository implements Repository<User> {
    private final DataSource dataSource;

    public UserRepository(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> getById(int id) throws SQLException {
        String query = """
                SELECT *
                FROM "User" WHERE Id_user = ?;
                """;

        Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet resultSet = ps.executeQuery();
        UserMapper userMapper = new UserMapper();

        if (resultSet.next()) {
            return Optional.of(userMapper.mapRowToObject(resultSet));
        }

        return Optional.empty();
    }

    @Override
    public List<User> getAll() throws SQLException {
        String query = """
                SELECT *
                FROM "User";
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet resultSet = ps.executeQuery();
            UserMapper userMapper = new UserMapper();

            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                users.add(userMapper.mapRowToObject(resultSet));
            }

            return users;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            log.error(e.getMessage(), e);
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void create(User user) throws SQLException {
        String query = """
                INSERT INTO "User"(Email, Password, LastName, FirstName, PatronymicName, PhoneNumber)
                VALUES (?, ?, ?, ?, ?, ?);
                """;

        try (Connection connection = dataSource.getConnection()) {

            UserMapper userMapper = new UserMapper();
            PreparedStatement ps = userMapper.mapObjectToRow(user, connection, query);
            ps.execute();

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void update(User user) throws SQLException {
        String query = """
                UPDATE "User"
                SET Email = ?, Password = ?, LastName = ?, FirstName = ?, PatronymicName = ?, PhoneNumber = ?
                    WHERE Id_user = ?;
                """;

        try (Connection connection = dataSource.getConnection()) {

            UserMapper userMapper = new UserMapper();
            PreparedStatement ps = userMapper.mapObjectToRow(user, connection, query);
            ps.setInt(1, user.getUserId());
            ps.execute();

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void delete(User user) throws SQLException {
        String query = """
                DELETE FROM "User"
                WHERE Id_user = ?;
                """;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, user.getUserId());
            ps.execute();

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new SQLException(e.getMessage());
        }
    }
}
