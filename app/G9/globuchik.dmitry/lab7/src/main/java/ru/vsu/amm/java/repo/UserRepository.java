package ru.vsu.amm.java.repo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import ru.vsu.amm.java.entity.UserEntity;

import java.util.List;

public class UserRepository implements Repository {

    private final DataSource dataSource;

    public UserRepository(final DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public Optional findById(Long id) throws SQLException {
        final String sql = "SELECT id, login, nickname, phonenumber, passwordhash, email FROM userentity WHERE id = ? ORDER BY id";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new UserEntity(
                            resultSet.getLong("id"),
                            resultSet.getString("login"),
                            resultSet.getString("nickname"),
                            resultSet.getString("phonenumber"),
                            resultSet.getString("passwordhash"),
                            resultSet.getString("email")
                    ));
                }
            }
            return Optional.empty();
        }
    }

    @Override
    public List<UserEntity> findAll() throws SQLException {
        final String sql = "SELECT id, login, nickname, phonenumber, passwordhash, email FROM userentity";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<UserEntity> users = new ArrayList<>();
                while (resultSet.next()) {
                    users.add(new UserEntity(resultSet.getLong("id"),
                            resultSet.getString("login"),
                            resultSet.getString("nickname"),
                            resultSet.getString("phonenumber"),
                            resultSet.getString("passwordhash"),
                            resultSet.getString("email")));
                }
                return users;
            }
        }
    }

    @Override
    public void update(Object object) {

    }

    @Override
    public void delete(Object object) {

    }

    @Override
    public void save(Object object) throws SQLException {

    }
}
