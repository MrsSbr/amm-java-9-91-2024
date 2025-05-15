package ru.vsu.amm.java.repositories;

import ru.vsu.amm.java.configuration.DbConfiguration;
import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.enums.Role;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepository implements Repository<UserEntity> {
    private static final Logger logger = Logger.getLogger(UserRepository.class.getName());
    private final DataSource dataSource;

    public UserRepository() {
        dataSource = DbConfiguration.getDataSource();
    }

    @Override
    public Optional<UserEntity> findById(Long id) throws SQLException {
        final String query = """
                SELECT User_Id, First_Name, Last_Name, Patronymic, City, Email, Phone_Number,
                "Password", "Role" FROM UserEntity WHERE User_Id = ?
                """;
        try (var connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, id);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();

            if (resultSet.next()) {
                Role role = Role.valueOf(resultSet.getString("Role"));
                return Optional.of(new UserEntity(
                        resultSet.getLong("User_id"),
                        resultSet.getString("First_Name"),
                        resultSet.getString("Last_Name"),
                        resultSet.getString("Patronymic"),
                        resultSet.getString("City"),
                        resultSet.getString("Email"),
                        resultSet.getString("Phone_Number"),
                        resultSet.getString("Password"),
                        role
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new SQLException(e.getMessage());
        }

        return Optional.empty();
    }

    public Optional<UserEntity> findByEmail(String email) throws SQLException {
        final String query = """
                SELECT User_Id, First_Name, Last_Name, Patronymic, City, Email, Phone_Number,
                "Password", "Role" FROM UserEntity WHERE Email = ?
                """;
        try (var connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, email);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();

            if (resultSet.next()) {
                Role role = Role.valueOf(resultSet.getString("Role"));
                return Optional.of(new UserEntity(
                        resultSet.getLong("User_id"),
                        resultSet.getString("First_Name"),
                        resultSet.getString("Last_Name"),
                        resultSet.getString("Patronymic"),
                        resultSet.getString("City"),
                        resultSet.getString("Email"),
                        resultSet.getString("Phone_Number"),
                        resultSet.getString("Password"),
                        role
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new SQLException(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<UserEntity> findAll() throws SQLException {
        List<UserEntity> users = new ArrayList<>();

        final String query = """
                SELECT User_Id, First_Name, Last_Name, Patronymic, City, Email, Phone_Number,
                "Password", "Role" FROM UserEntity
                """;

        try (var connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                Role role = Role.valueOf(resultSet.getString("Role"));
                users.add(new UserEntity(
                        resultSet.getLong("User_id"),
                        resultSet.getString("First_Name"),
                        resultSet.getString("Last_Name"),
                        resultSet.getString("Patronymic"),
                        resultSet.getString("City"),
                        resultSet.getString("Email"),
                        resultSet.getString("Phone_Number"),
                        resultSet.getString("Password"),
                        role
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new SQLException(e.getMessage());
        }

        return users;
    }

    @Override
    public void update(UserEntity entity) throws SQLException {
        final String query = """
                UPDATE UserEntity SET First_Name = ?, Last_Name = ?, Patronymic = ?,
                City = ?, Email = ?, Phone_Number = ?, "Password" = ?, "Role" = ? WHERE User_Id = ?
                """;

        try (var connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getPatronymic());
            preparedStatement.setString(4, entity.getCity());
            preparedStatement.setString(5, entity.getEmail());
            preparedStatement.setString(6, entity.getPhoneNumber());
            preparedStatement.setString(7, entity.getPassword());
            preparedStatement.setString(8, entity.getRole().name());
            preparedStatement.setLong(9, entity.getUserId());

            preparedStatement.execute();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void save(UserEntity entity) throws SQLException {
        final String query = """
                INSERT INTO UserEntity (First_Name, Last_Name, Patronymic, City, Email,
                Phone_Number, "Password", "Role") VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (var connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getPatronymic());
            preparedStatement.setString(4, entity.getCity());
            preparedStatement.setString(5, entity.getEmail());
            preparedStatement.setString(6, entity.getPhoneNumber());
            preparedStatement.setString(7, entity.getPassword());
            preparedStatement.setString(8, entity.getRole().name());

            preparedStatement.execute();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void delete(UserEntity entity) throws SQLException {
        final String query = "DELETE FROM UserEntity WHERE User_Id = ?";

        try (var connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, entity.getUserId());

            preparedStatement.execute();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new SQLException(e.getMessage());
        }
    }
}
