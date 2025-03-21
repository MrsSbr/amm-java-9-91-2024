package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.Configuration.DatabaseConfiguration;
import ru.vsu.amm.java.Entities.UserEntity;
import ru.vsu.amm.java.Enums.Roles;

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
        final String query = "SELECT UserID, UserName, Password, Role, Phone, BirthDate FROM UserTable WHERE id = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);

        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();

        if (resultSet.next()) {
            Roles role = Roles.valueOf(resultSet.getString("Role"));
            return Optional.of(new UserEntity(
                    resultSet.getLong("UserID"),
                    resultSet.getString("UserName"),
                    resultSet.getString("Password"),
                    role,
                    resultSet.getString("Phone"),
                    resultSet.getDate("BirthDate")
            ));
        }

        return Optional.empty();
    }

    @Override
    public List<UserEntity> findAll() throws SQLException {
        final String query = "SELECT UserID, UserName, Password, Role, Phone, BirthDate FROM UserTable WHERE id = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.getResultSet();

        List<UserEntity> users = new ArrayList<>();
        while (resultSet.next()) {
            Roles role = Roles.valueOf(resultSet.getString("Role"));
            users.add(new UserEntity(
                    resultSet.getLong("UserID"),
                    resultSet.getString("UserName"),
                    resultSet.getString("Password"),
                    role,
                    resultSet.getString("Phone"),
                    resultSet.getDate("BirthDate")
            ));
        }

        return users;
    }

    @Override
    public void save(UserEntity entity) throws SQLException {
        final String query = "INSERT INTO UserTable (UserName, Password, Role, Phone, BirthDate) VALUES (?, ?, ?, ?, ?)";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, entity.getUserName());
        preparedStatement.setString(2, entity.getPassword());
        preparedStatement.setString(3, entity.getRole().name());
        preparedStatement.setString(4, entity.getPhone());
        preparedStatement.setDate(5, Date.valueOf(entity.getBirthDate()));

        preparedStatement.execute();
    }

    @Override
    public void delete(UserEntity entity) throws SQLException {
        final String query = "DELETE FROM UserTable WHERE UserID = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, entity.getUserID());

        preparedStatement.execute();
    }

    @Override
    public void update(UserEntity entity) throws SQLException {
        final String query = "UPDATE UserTable SET UserName = ?, Password = ?, Role = ?, Phone = ? WHERE UserID = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, entity.getUserName());
        preparedStatement.setString(2, entity.getPassword());
        preparedStatement.setLong(3, entity.getUserID());
        preparedStatement.setString(4, entity.getUserRole());
        preparedStatement.setString(5, entity.getPhone());

        preparedStatement.execute();
    }
}
