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
    public Optional<EmployeeEntity> getById(int id, boolean isForUpdate) throws SQLException {
        String query = """
                SELECT employee_id, hotel_id, login, password, name, phone_number,
                email, passport_number, passport_series, post, birthday
                FROM employee WHERE employee_id = ?""";
        query = makeSelectQueryForUpdateOrNot(query, isForUpdate);

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
    public List<EmployeeEntity> getAll(boolean isForUpdate) throws SQLException {
        String query = """
                SELECT employee_id, hotel_id, login, password, name, phone_number, email,
                passport_number, passport_series, post, birthday
                FROM employee
                FOR UPDATE""";
        query = makeSelectQueryForUpdateOrNot(query, isForUpdate);

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
                passport_number = ?, passport_series = ?, post = ?, birthday = ?
                WHERE employee_id = ?""";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        setPreparedStatement(preparedStatement, entity);
        preparedStatement.setInt(11, entity.getId());
        preparedStatement.execute();
    }

    @Override
    public EmployeeEntity save(EmployeeEntity entity) throws SQLException {
        final String query = """
                INSERT INTO employee (hotel_id, login, password, name, phone_number,
                email, passport_number, passport_series, post, birthday)
                VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        setPreparedStatement(preparedStatement, entity);
        preparedStatement.execute();

        return configureEmployeeEntityFromResultSet(preparedStatement.getResultSet());
    }

    public void save(String login, String password, EmployeePost post, Integer hotelId) throws SQLException {
        final String query = """
                INSERT INTO employee (login, password, post, hotel_id)
                VALUES(?, ?, ?, ?)""";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, post.toString());
        preparedStatement.setInt(4, hotelId);
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

    public Optional<EmployeeEntity> getByLogin(String login, boolean isForUpdate) throws SQLException {
        String query = """
                SELECT employee_id, hotel_id, login, password, name, phone_number,
                email, passport_number, passport_series, post, birthday
                FROM employee WHERE login LIKE ?""";
        query = makeSelectQueryForUpdateOrNot(query, isForUpdate);

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

    public List<EmployeeEntity> getAllByParameters(Integer employee_id, Integer hotel_id, String login,
                                               String name, String phone_number, String email,
                                               String passport_number, String passport_series,
                                               String post, LocalDate birthday, boolean isForUpdate) throws SQLException {
        String query = """
                SELECT employee_id, hotel_id, login, password, name, phone_number,
                email, passport_number, passport_series, post, birthday
                FROM employee
                WHERE COALESCE(?, -1) = employee_id
                    OR COALESCE(?, -1) = hotel_id
                    OR COALESCE(?, '') LIKE login
                    OR COALESCE(?, '') LIKE name
                    OR COALESCE(?, '') LIKE phone_number
                    OR COALESCE(?, '') LIKE email
                    OR COALESCE(?, '') LIKE passport_number
                        AND COALESCE(?, '') LIKE passport_series
                    OR COALESCE(?, '') LIKE post
                    OR COALESCE(?, now()) = birthday""";
        query = makeSelectQueryForUpdateOrNot(query, isForUpdate);


        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);

        if (employee_id == null) {
            preparedStatement.setNull(1, Types.INTEGER);
        } else {
            preparedStatement.setInt(1, employee_id);
        }

        if (hotel_id == null) {
            preparedStatement.setNull(2, Types.INTEGER);
        } else {
            preparedStatement.setInt(2, hotel_id);
        }

        if (login == null) {
            preparedStatement.setNull(3, Types.VARCHAR);
        } else {
            preparedStatement.setString(3, login);
        }

        if (name == null) {
            preparedStatement.setNull(4, Types.VARCHAR);
        } else {
            preparedStatement.setString(4, name);
        }

        if (phone_number == null) {
            preparedStatement.setNull(5, Types.VARCHAR);
        } else {
            preparedStatement.setString(5, phone_number);
        }

        if (email == null) {
            preparedStatement.setNull(6, Types.VARCHAR);
        } else {
            preparedStatement.setString(6, email);
        }

        if (passport_number == null) {
            preparedStatement.setNull(7, Types.VARCHAR);
        } else {
            preparedStatement.setString(7, passport_number);
        }

        if (passport_series == null) {
            preparedStatement.setNull(8, Types.VARCHAR);
        } else {
            preparedStatement.setString(8, passport_series);
        }

        if (post == null) {
            preparedStatement.setNull(9, Types.VARCHAR);
        } else {
            preparedStatement.setString(9, post);
        }

        if (birthday == null) {
            preparedStatement.setNull(10, Types.DATE);
        } else {
            preparedStatement.setDate(10, Date.valueOf(birthday));
        }

        preparedStatement.execute();

        List<EmployeeEntity> entityList = new ArrayList<>();

        var resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {
            entityList.add(configureEmployeeEntityFromResultSet(resultSet));
        }

        return entityList;
    }

    private String makeSelectQueryForUpdateOrNot(String query, boolean isForUpdate) {
        if (isForUpdate) {
            query += " FOR UPDATE";
        }
        return query;
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
        if (entity.getBirthday() == null) {
            preparedStatement.setNull(10, Types.DATE);
        } else {
            preparedStatement.setDate(10, Date.valueOf(entity.getBirthday()));
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
                birthday
        );
    }
}
