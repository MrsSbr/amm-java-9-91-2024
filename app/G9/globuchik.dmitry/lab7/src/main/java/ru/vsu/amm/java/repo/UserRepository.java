package ru.vsu.amm.java.repo;

import ru.vsu.amm.java.entity.UserEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements Repository<UserEntity> {

    private final DataSource dataSource;

    public UserRepository(final DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public Optional<UserEntity> findById(Long id) throws SQLException {
        final String sql = "SELECT id, login, nickname, phonenumber, passwordhash, email, salt FROM userentity WHERE id = ? ORDER BY id";
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
                            resultSet.getString("email"),
                            resultSet.getString("salt")
                    ));
                }
            }
            return Optional.empty();
        }
    }

    @Override
    public List<UserEntity> findAll() throws SQLException {
        final String sql = "SELECT id, login, nickname, phonenumber, passwordhash,email, salt, login_count FROM userentity";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<UserEntity> users = new ArrayList<>();
                while (resultSet.next()) {
                    ResultSet reslutSet;
                    users.add(new UserEntity(resultSet.getLong("id"),
                            resultSet.getString("login"),
                            resultSet.getString("nickname"),
                            resultSet.getString("phonenumber"),
                            resultSet.getString("passwordhash"),
                            resultSet.getString("email"),
                            resultSet.getString("salt"),
                            resultSet.getInt("login_count")));
                }
                return users;
            }
        }
    }

    public UserEntity findByLogin(String login) throws SQLException {
        final String sql = "SELECT id, login, nickname, phonenumber, passwordhash, email, salt, login_count FROM userentity WHERE login = ? ORDER BY id";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, login);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new UserEntity(
                            resultSet.getLong("id"),
                            resultSet.getString("login"),
                            resultSet.getString("nickname"),
                            resultSet.getString("phonenumber"),
                            resultSet.getString("passwordhash"),
                            resultSet.getString("email"),
                            resultSet.getString("salt"),
                            resultSet.getInt("login_count")
                    );
                }
            }
            return null;
        }
    }

    @Override
    public void update(UserEntity user) throws SQLException {
        String sql = "UPDATE userentity SET login = ?, nickname = ?, phonenumber = ?, "
                + "passwordhash = ?, salt = ?, email = ?, login_count = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getNickname());
            statement.setString(3, user.getPhoneNumber());
            statement.setString(4, user.getPasswordHash());
            statement.setString(5, user.getSalt());
            statement.setString(6, user.getEmail());
            statement.setInt(7, user.getLoginCount());
            statement.setLong(8, user.getId());

            statement.executeUpdate();
        }
    }

    @Override
    public void delete(UserEntity object) {

    }

    @Override
    public boolean save(UserEntity user) throws SQLException {
        String sql = "INSERT INTO public.userentity (login, nickname, phonenumber, passwordhash, salt, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getNickname());
            statement.setString(3, user.getPhoneNumber());
            statement.setString(4, user.getPasswordHash());
            statement.setString(5, user.getSalt());
            statement.setString(6, user.getEmail());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                }
            }
            return true;
        }
    }
}
