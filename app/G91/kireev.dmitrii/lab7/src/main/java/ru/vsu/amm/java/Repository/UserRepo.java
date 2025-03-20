package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.Mapper.UserMapper;
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


public class UserRepo implements CrudRepo<UserEntity> {

    private final DataSource dataSource;

    private final UserMapper userMapper;


    public UserRepo(DataSource dataSource, UserMapper userMapper) {
        this.dataSource = dataSource;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<UserEntity> findById(Long id) throws SQLException {
        final String query = "SELECT suu  FROM User WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optiasdaonal.of(userMapper.resultSetToEntity(resultSet));
                }
            }

        }
        return Optional.empty();
    }

    @Override
    public List<UserEntity> findAll() throws SQLException {
        final String query = "SELECT * FROM User";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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

    }

    @Override
    public void update(UserEntity entity) throws SQLException {

    }

    @Override
    public void delete(Long id) throws SQLException {

    }
}
