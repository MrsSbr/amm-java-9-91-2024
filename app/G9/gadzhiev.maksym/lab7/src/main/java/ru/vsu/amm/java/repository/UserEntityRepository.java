package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.config.DatabaseConfig;
import ru.vsu.amm.java.entity.UserEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserEntityRepository implements BaseRepository<UserEntity> {
    private final DataSource dataSource;

    public UserEntityRepository() {
        dataSource = DatabaseConfig.getDataSource();
    }

    @Override
    public Optional<UserEntity> findById(Long id) throws SQLException {
        final String query = """
                SELECT id, login, password, number, email
                FROM userEntity WHERE id = ?
                """;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement pStmt = connection.prepareStatement(query)) {

            pStmt.setLong(1, id);

            try (ResultSet result = pStmt.executeQuery()) {
                if (result.next()) {
                    return Optional.of(new UserEntity(
                            result.getLong("id"),
                            result.getString("login"),
                            result.getString("password"),
                            result.getString("number"),
                            result.getString("email")
                    ));
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public void update(UserEntity entity) throws SQLException {
        final String query = """
                UPDATE userEntity
                SET login = ?, password = ?, number = ?, email = ?
                WHERE id = ?
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pStmt = connection.prepareStatement(query)) {
            setPrepareStatement(pStmt, entity);
            pStmt.setLong(5, entity.getId());
            pStmt.execute();
        }
    }

    private void setPrepareStatement(PreparedStatement pStmt, UserEntity entity) throws SQLException {
        pStmt.setString(1, entity.getLogin());
        pStmt.setString(2, entity.getPassword());
        pStmt.setString(3, entity.getNumber());
        pStmt.setString(4, entity.getEmail());
    }

    @Override
    public boolean save(UserEntity entity) throws SQLException {
        final String query = """
                INSERT INTO userEntity (login, password, number, email)
                VALUES (?, ?, ?, ?)
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pStmt = connection.prepareStatement(query)) {
            setPrepareStatement(pStmt, entity);
            return pStmt.executeUpdate() == 1;
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        final String query = "DELETE FROM userEntity WHERE id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement pStmt = connection.prepareStatement(query)) {
            pStmt.setLong(1, id);
            pStmt.execute();
        }
    }

    public Optional<UserEntity> findByLogin(String login) throws SQLException {
        final String query = """
                SELECT id, login, password, number_phone, email
                FROM user_entity WHERE login = ?
                """;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, login);

            try(ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    return Optional.of(new UserEntity(
                            result.getLong("id"),
                            result.getString("login"),
                            result.getString("password"),
                            result.getString("number_phone"),
                            result.getString("email")));
                }
                return Optional.empty();
            }
        }
    }
}
