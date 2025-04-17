package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.dbConnection.DatabaseConfiguration;
import ru.vsu.amm.java.entities.EmployeeEntity;
import ru.vsu.amm.java.enums.EmployeePost;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeRepo implements CrudRepo<EmployeeEntity> {
    private final DataSource dataSource;

    public EmployeeRepo() {
        dataSource = DatabaseConfiguration.getDataSource();

    }

    @Override
    public Optional<EmployeeEntity> getById(int id) throws SQLException {
        final String query = """
                SELECT employee_id, hotel_id, login, password, name, phone_number,
                email, passport_number, passport_series, post, salary, birthday
                FROM employee WHERE employee_id = ?""";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();

        var resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            return Optional.of(configureEmployeeEntityFromResultSet(resultSet));
        }

        return Optional.empty();
    }

    @Override
    public List<EmployeeEntity> getAll() throws SQLException {
        final String query = """
                SELECT employee_id, hotel_id, login, password, name, phone_number, email,
                passport_number, passport_series, post, salary, birthday
                FROM employee""";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();

        List<EmployeeEntity> entityList = new ArrayList<>();

        var resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {
            entityList.add(configureEmployeeEntityFromResultSet(resultSet));
        }

        return entityList;
    }

    @Override
    public void update(EmployeeEntity entity) throws SQLException {
        final String query = """
                UPDATE employee
                SET hotel_id = ?, login = ?, password = ?, name = ?, phone_number = ?, email = ?,
                passport_number = ?, passport_series = ?, post = ?, salary = ?, birthday = ?
                WHERE employee_id = ?""";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        setPreparedStatement(preparedStatement, entity);
        preparedStatement.setInt(12, entity.getId());
        preparedStatement.execute();
    }

    @Override
    public void save(EmployeeEntity entity) throws SQLException {
        final String query = """
                INSERT INTO employee (hotel_id, login, password, name, phone_number,
                email, passport_number, passport_series, post, salary, birthday)
                VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        setPreparedStatement(preparedStatement, entity);
        preparedStatement.execute();
    }

    public void save(String login, String password, EmployeePost post) throws SQLException {
        final String query = """
                INSERT INTO employee (login, password, post)
                VALUES(?, ?, ?)""";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, post.toString());
        preparedStatement.execute();
    }

    @Override
    public void delete(int id) throws SQLException {
        final String query = "DELETE FROM employee WHERE employee_id = ?";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    public Optional<EmployeeEntity> getByLogin(String login) throws SQLException {
        final String query = """
                SELECT employee_id, hotel_id, login, password, name, phone_number,
                email, passport_number, passport_series, post, salary, birthday
                FROM employee WHERE login LIKE ?""";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, login);
        preparedStatement.execute();

        var resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            return Optional.of(configureEmployeeEntityFromResultSet(resultSet));
        }

        return Optional.empty();
    }

    private void setPreparedStatement(PreparedStatement preparedStatement, EmployeeEntity entity) throws SQLException {
        if (entity.getHotelId() == null) {
            preparedStatement.setNull(1, Types.INTEGER);
        } else {
            preparedStatement.setInt(1, entity.getHotelId());
        }
        preparedStatement.setString(2, entity.getLogin());
        if (entity.getPassword() != null) {
            preparedStatement.setString(3, entity.getPassword());
        }
        if (entity.getName() == null) {
            preparedStatement.setNull(4, Types.VARCHAR);
        } else {
            preparedStatement.setString(4, entity.getName());
        }
        if (entity.getPhoneNumber() == null) {
            preparedStatement.setNull(5, Types.CHAR);
        } else {
            preparedStatement.setString(5, entity.getPhoneNumber());
        }
        if (entity.getEmail() == null) {
            preparedStatement.setNull(6, Types.VARCHAR);
        } else {
            preparedStatement.setString(6, entity.getEmail());
        }
        if (entity.getPassportNumber() == null) {
            preparedStatement.setNull(7, Types.CHAR);
        } else {
            preparedStatement.setString(7, entity.getPassportNumber());
        }
        if (entity.getPassportSeries() == null) {
            preparedStatement.setNull(8, Types.CHAR);
        } else {
            preparedStatement.setString(8, entity.getPassportSeries());
        }
        preparedStatement.setString(9, entity.getPost().toString());
        if (entity.getSalary() == null) {
            preparedStatement.setNull(10, Types.INTEGER);
        } else {
            preparedStatement.setInt(10, entity.getSalary());
        }
        if (entity.getBirthday() == null) {
            preparedStatement.setNull(11, Types.DATE);
        } else {
            preparedStatement.setDate(11, Date.valueOf(entity.getBirthday()));
        }
    }

    private EmployeeEntity configureEmployeeEntityFromResultSet(ResultSet resultSet) throws SQLException {
        var birthdayDate = resultSet.getDate("birthday");
        LocalDate birthday = null;
        if (birthdayDate != null) {
            birthday = birthdayDate.toLocalDate();
        }
        return new EmployeeEntity(
                resultSet.getInt("employee_id"),
                resultSet.getInt("hotel_id"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                resultSet.getString("name"),
                resultSet.getString("phone_number"),
                resultSet.getString("email"),
                resultSet.getString("passport_number"),
                resultSet.getString("passport_series"),
                EmployeePost.valueOf(resultSet.getString("post")),
                resultSet.getInt("salary"),
                birthday
        );
    }
}
