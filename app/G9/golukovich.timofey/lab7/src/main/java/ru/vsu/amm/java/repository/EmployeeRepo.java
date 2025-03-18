package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.dbConnection.DatabaseConfiguration;
import ru.vsu.amm.java.entities.EmployeeEntity;
import ru.vsu.amm.java.enums.EmployeePost;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeRepo implements IRepo<EmployeeEntity> {
    private final DataSource dataSource;

    public EmployeeRepo() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<EmployeeEntity> getById(int id) throws SQLException {
        final String query = "SELECT id, hotelId, login, password, name, "
                + "phoneNumber, email, passportNumber, passportSeries, "
                + "post, salary, birthday FROM employee WHERE id = ?";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();

        var resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            return Optional.of(new EmployeeEntity(
                    resultSet.getInt("id"),
                    resultSet.getInt("hotelId"),
                    resultSet.getString("login"),
                    resultSet.getString("password"),
                    resultSet.getString("name"),
                    resultSet.getString("phoneNumber"),
                    resultSet.getString("email"),
                    resultSet.getString("passportNumber"),
                    resultSet.getString("passportSeries"),
                    EmployeePost.valueOf(resultSet.getString("post")),
                    resultSet.getInt("salary"),
                    resultSet.getDate("birthday").toLocalDate())
            );
        }

        return Optional.empty();
    }

    @Override
    public List<EmployeeEntity> getAll() throws SQLException {
        final String query = "SELECT id, hotelId, login, password, name, "
                + "phoneNumber, email, passportNumber, passportSeries, "
                + "post, salary, birthday FROM employee";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();

        List<EmployeeEntity> entityList = new ArrayList<>();

        var resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {
            entityList.add(new EmployeeEntity(
                    resultSet.getInt("id"),
                    resultSet.getInt("hotelId"),
                    resultSet.getString("login"),
                    resultSet.getString("password"),
                    resultSet.getString("name"),
                    resultSet.getString("phoneNumber"),
                    resultSet.getString("email"),
                    resultSet.getString("passportNumber"),
                    resultSet.getString("passportSeries"),
                    EmployeePost.valueOf(resultSet.getString("post")),
                    resultSet.getInt("salary"),
                    resultSet.getDate("birthday").toLocalDate()));
        }

        return entityList;
    }

    @Override
    public void update(EmployeeEntity entity) throws SQLException {
        final String query = "UPDATE employee SET hotelId = ?, login = ?, password = ?, name = ?, "
                + "phoneNumber = ?, email = ?, passportNumber = ?, passportSeries = ?, "
                + "post = ?, salary = ?, birthday = ? WHERE id = ?";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, entity.getHotelId());
        preparedStatement.setString(2, entity.getLogin());
        preparedStatement.setString(3, entity.getPassword());
        preparedStatement.setString(4, entity.getName());
        preparedStatement.setString(5, entity.getPhoneNumber());
        preparedStatement.setString(6, entity.getEmail());
        preparedStatement.setString(7, entity.getPassportNumber());
        preparedStatement.setString(8, entity.getPassportSeries());
        preparedStatement.setString(9, entity.getPost().toString());
        preparedStatement.setInt(10, entity.getSalary());
        preparedStatement.setDate(11, Date.valueOf(entity.getBirthday()));
        preparedStatement.setInt(12, entity.getId());
        preparedStatement.execute();
    }

    @Override
    public void save(EmployeeEntity entity) throws SQLException {
        final String query = "INSERT INTO employee (hotelId, login, password, name, "
                + "phoneNumber, email, passportNumber, passportSeries, "
                + "post, salary, birthday) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, entity.getHotelId());
        preparedStatement.setString(2, entity.getLogin());
        preparedStatement.setString(3, entity.getPassword());
        preparedStatement.setString(4, entity.getName());
        preparedStatement.setString(5, entity.getPhoneNumber());
        preparedStatement.setString(6, entity.getEmail());
        preparedStatement.setString(7, entity.getPassportNumber());
        preparedStatement.setString(8, entity.getPassportSeries());
        preparedStatement.setString(9, entity.getPost().toString());
        preparedStatement.setInt(10, entity.getSalary());
        preparedStatement.setDate(11, Date.valueOf(entity.getBirthday()));
        preparedStatement.execute();
    }

    @Override
    public void delete(int id) throws SQLException {
        final String query = "DELETE FROM employee WHERE id = ?";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }
}
