package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.Configuration.DatabaseConfiguration;
import ru.vsu.amm.java.Entities.UserEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements DatabaseRepository<UserEntity> {

    private final DataSource dataSource;

    public UserRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<UserEntity> findById(Long id) throws SQLException {
        final String query = "SELECT UserID, UserName, Password FROM UserTable WHERE id = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();

        if (resultSet.next()) {
            return Optional.of(new UserEntity(
                    resultSet.getLong("UserID"),
                    resultSet.getString("UserName"),
                    resultSet.getString("Password")
            ));
        }
        return Optional.empty();
    }

    @Override
    public List<UserEntity> findAll() throws SQLException {
        final String query = "SELECT UserID, UserName, Password FROM UserTable WHERE id = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();

        List<UserEntity> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(new UserEntity(
                    resultSet.getLong("UserID"),
                    resultSet.getString("UserName"),
                    resultSet.getString("Password")
            ));
        }

        return users;
    }


}
