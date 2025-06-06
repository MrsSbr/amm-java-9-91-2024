package ru.vsu.amm.java.Repository.Impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.vsu.amm.java.Config.DatabaseConfig;
import ru.vsu.amm.java.Mapper.Impl.UserMapper;
import ru.vsu.amm.java.Model.Entity.UserEntity;
import ru.vsu.amm.java.Repository.Interface.CrudRepo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class UserRepo implements CrudRepo<UserEntity> {

    private final DataSource dataSource;
    private final UserMapper userMapper;

    public UserRepo() {
        this.dataSource = DatabaseConfig.getDataSource();
        this.userMapper = new UserMapper();
    }

    @Override
    public Optional<UserEntity> findById(Long id) throws SQLException {
        final String query = "SELECT user_id,name,email,password,phone FROM \"user\" WHERE user_id = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(userMapper.resultSetToEntity(resultSet));
                }
            }

        }
        return Optional.empty();
    }

    @Override
    public List<UserEntity> findAll() throws SQLException {
        final String query = "SELECT user_id,name,email,password,phone FROM \"user\"";
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<UserEntity> users = new ArrayList<>();
                while (resultSet.next()) {
                    users.add(userMapper.resultSetToEntity(resultSet));
                }
                return users;
            }
        }
    }

    @Override
    public void save(UserEntity entity) throws SQLException {
        final String query = "INSERT INTO \"user\"(name, email, password, phone) VALUES(?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setString(3, entity.getPassword());
            preparedStatement.setString(4, entity.getPhone());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(UserEntity entity) throws SQLException {
        final String query = "UPDATE \"user\" SET name = ?, password = ? WHERE user_id = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setLong(3, entity.getUserId());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setUserId(generatedKeys.getLong(1));
                }
            }
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        final String query = "DELETE FROM \"user\" WHERE user_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }

    public Optional<UserEntity> findByEmail(String email) throws SQLException {
        final String query = "SELECT user_id, name, email, password, phone FROM \"user\" WHERE email = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    log.info(userMapper.resultSetToEntity(resultSet).toString());
                    return Optional.of(userMapper.resultSetToEntity(resultSet));
                }
            }
        }
        return Optional.empty();
    }
}
