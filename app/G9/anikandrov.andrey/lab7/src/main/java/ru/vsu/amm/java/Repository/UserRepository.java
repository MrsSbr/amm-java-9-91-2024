package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.Configuration.DatabaseConfiguration;
import ru.vsu.amm.java.Entities.UserEntity;
import ru.vsu.amm.java.Enums.Roles;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Types;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepository implements DatabaseRepository<UserEntity> {

    private final DataSource dataSource;

    private static final Logger logger = Logger.getLogger(UserRepository.class.getName());

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public UserRepository() {
        this.dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<UserEntity> findById(Long id) throws SQLException {
        logger.log(Level.INFO, "Try to find user by ID: " + id);
        final String query = "SELECT User_ID, User_Name, User_Password, User_Role, Phone, Birth_Date FROM User_Table WHERE User_ID = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);

        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();

        if (resultSet.next()) {
            Roles role = Roles.valueOf(resultSet.getString("User_Role"));
            Date birthDate = resultSet.getDate("Birth_Date");
            LocalDate localBirthDate = (birthDate != null) ? birthDate.toLocalDate() : null;

            logger.log(Level.INFO, "Successfully found user with ID: " + id);

            return Optional.of(new UserEntity(resultSet.getLong("User_ID"), resultSet.getString("User_Name"), resultSet.getString("User_Password"), role, resultSet.getString("Phone"), localBirthDate));
        }

        logger.log(Level.WARNING, "User not found with ID: " + id);
        return Optional.empty();
    }

    public Optional<UserEntity> findByUserName(String username) throws SQLException {
        logger.log(Level.INFO, "Try to find user by username: " + username);

        final String query = "SELECT User_ID, User_Name, User_Password, User_Role, Phone, Birth_Date FROM User_Table WHERE User_Name = ?";

        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Roles role = Roles.valueOf(resultSet.getString("User_Role"));

                    Date birthDate = resultSet.getDate("Birth_Date");
                    LocalDate localBirthDate = (birthDate != null) ? birthDate.toLocalDate() : null;

                    logger.log(Level.INFO, "Successfully found user with username: " + username);

                    return Optional.of(new UserEntity(resultSet.getLong("User_ID"), resultSet.getString("User_Name"), resultSet.getString("User_Password"), role, resultSet.getString("Phone"), localBirthDate));
                }
            }
        }

        logger.log(Level.WARNING, "User not found with username: " + username);

        return Optional.empty();
    }

    @Override
    public List<UserEntity> findAll() throws SQLException {
        logger.log(Level.INFO, "Try to find all users");

        final String query = "SELECT User_ID, User_Name, User_Password, User_Role, Phone, Birth_Date FROM User_Table";

        List<UserEntity> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Roles role = Roles.valueOf(resultSet.getString("User_Role"));
                users.add(new UserEntity(resultSet.getLong("User_ID"), resultSet.getString("User_Name"), resultSet.getString("User_Password"), role, resultSet.getString("Phone"), resultSet.getDate("Birth_Date").toLocalDate()));
            }
        }

        logger.log(Level.INFO, "Found " + users.size() + " users");
        return users;
    }

    @Override
    public void save(UserEntity entity) throws SQLException {
        logger.log(Level.INFO, "Try to save user: " + entity.getUserName());

        final String query = "INSERT INTO User_Table (User_Name, User_Password, User_Role, Phone, Birth_Date) VALUES (?, ?, ?, ?, ?)";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, entity.getUserName());
        preparedStatement.setString(2, entity.getUserPassword());

        if (entity.getUserRole() == null) {
            preparedStatement.setNull(3, Types.VARCHAR);
        } else {
            preparedStatement.setString(3, entity.getUserRole().name());
        }

        if (entity.getPhone() == null || entity.getPhone().isEmpty()) {
            preparedStatement.setNull(4, Types.VARCHAR);
        } else {
            preparedStatement.setString(4, entity.getPhone());
        }

        if (entity.getBirthDate() == null) {
            preparedStatement.setNull(5, Types.DATE);
        } else {
            preparedStatement.setDate(5, Date.valueOf(entity.getBirthDate()));
        }

        preparedStatement.execute();

        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                entity.setUserID(generatedKeys.getLong(1));
                logger.log(Level.INFO, "Successfully saved user with ID: " + entity.getUserID());
            } else {
                logger.log(Level.SEVERE, "Save Error - ID error for user: " + entity.getUserName());
                throw new SQLException("Save Error - ID error");
            }
        }
    }

    @Override
    public void delete(UserEntity entity) throws SQLException {
        logger.log(Level.INFO, "Try to delete user with ID: " + entity.getUserID());

        final String query = "DELETE FROM User_Table WHERE User_ID = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, entity.getUserID());

        preparedStatement.execute();
        logger.log(Level.INFO, "Successfully deleted user with ID: " + entity.getUserID());
    }

    @Override
    public void update(UserEntity entity) throws SQLException {
        logger.log(Level.INFO, "Try to update user with ID: " + entity.getUserID());

        final String query = "UPDATE User_Table SET User_Name = ?, User_Password = ?  WHERE User_ID = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, entity.getUserName());
        preparedStatement.setString(2, entity.getUserPassword());
        preparedStatement.setLong(3, entity.getUserID());

        preparedStatement.execute();
    }
}
