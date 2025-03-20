package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.Configuration.DatabaseConfiguration;
import ru.vsu.amm.java.Entities.UserEntity;
import ru.vsu.amm.java.Enums.Roles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;
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
        final String query = "SELECT User_ID, User_Name, User_Password, User_Role, Phone, Birth_Date FROM User_Table WHERE User_ID = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);

        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();

        if (resultSet.next()) {
            Roles role = Roles.valueOf(resultSet.getString("User_Role"));
            return Optional.of(new UserEntity(
                    resultSet.getLong("User_ID"),
                    resultSet.getString("User_Name"),
                    resultSet.getString("User_Password"),
                    role,
                    resultSet.getString("Phone"),
                    resultSet.getDate("Birth_Date").toLocalDate()
            ));
        }

        return Optional.empty();
    }

    public Optional<UserEntity> findByUserName(String username) throws SQLException {
        final String query = "SELECT User_ID, User_Name, User_Password, User_Role, Phone, Birth_Date FROM User_Table WHERE User_Name = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);

        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();

        if (resultSet.next()) {
            Roles role = Roles.valueOf(resultSet.getString("User_Role"));
            return Optional.of(new UserEntity(
                    resultSet.getLong("User_ID"),
                    resultSet.getString("User_Name"),
                    resultSet.getString("User_Password"),
                    role,
                    resultSet.getString("Phone"),
                    resultSet.getDate("Birth_Date").toLocalDate()
            ));
        }

        return Optional.empty();
    }

    @Override
    public List<UserEntity> findAll() throws SQLException {
        final String query = "SELECT User_ID, User_Name, User_Password, User_Role, Phone, Birth_Date FROM User_Table";

        List<UserEntity> users = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Roles role = Roles.valueOf(resultSet.getString("User_Role"));
                users.add(new UserEntity(
                        resultSet.getLong("User_ID"),
                        resultSet.getString("User_Name"),
                        resultSet.getString("User_Password"),
                        role,
                        resultSet.getString("Phone"),
                        resultSet.getDate("Birth_Date").toLocalDate()
                ));
            }
        }

        return users;
    }

    @Override
    public void save(UserEntity entity) throws SQLException {
        final String query = "INSERT INTO User_Table (User_Name, User_Password, User_Role, Phone, Birth_Date) VALUES (?, ?, ?, ?, ?)";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);


        preparedStatement.setString(1, entity.getUserName());
        preparedStatement.setString(2, entity.getUserPassword());
        preparedStatement.setString(3, entity.getUserRole().name());
        preparedStatement.setString(4, entity.getPhone());
        preparedStatement.setDate(5, Date.valueOf(entity.getBirthDate()));

        preparedStatement.execute();
    }

    @Override
    public void delete(UserEntity entity) throws SQLException {
        final String query = "DELETE FROM User_Table WHERE User_ID = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, entity.getUserID());

        preparedStatement.execute();
    }

    @Override
    public void update(UserEntity entity) throws SQLException {
        final String query = "UPDATE User_Table SET User_Name = ?, User_Password = ?  WHERE User_ID = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, entity.getUserName());
        preparedStatement.setString(2, entity.getUserPassword());
        preparedStatement.setLong(3, entity.getUserID());

        preparedStatement.execute();
    }
}
