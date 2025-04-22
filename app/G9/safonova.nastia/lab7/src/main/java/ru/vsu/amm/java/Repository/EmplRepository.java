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

public class EmplRepository implements DatabaseRepository<UserEntity> {

    private final DataSource dataSource;

    public EmplRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<Employee> findById(Long id) throws SQLException {
        final String query = "SELECT IdEmpl, SurnameEmpl, NameEmpl, PatronumicEmpl, DateOfBirthEmpl FROM Employee WHERE id = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);

        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();

        if (resultSet.next()) {
            return Optional.of(new Employee(
                    resultSet.getLong("IdEmpl"),
                    resultSet.getString("SurnameEmpl"),
                    resultSet.getString("NameEmpl"),
                    resultSet.getString("PatronumicEmpl"),
                    resultSet.getLocalDate("DateOfBirthEmpl"),
            ));
        }

        return Optional.empty();
    }

    @Override
    public List<Employee> findAll() throws SQLException {
        final String query = "SELECT IdEmpl, SurnameEmpl, NameEmpl, PatronumicEmpl, DateOfBirthEmpl FROM Employee WHERE id = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.getResultSet();

        List<Employee> empls = new ArrayList<>();
        while (resultSet.next()) {
            empls.add(new Employee(
                    resultSet.getLong("IdEmpl"),
                    resultSet.getString("SurnameEmpl"),
                    resultSet.getString("NameEmpl"),
                    resultSet.getString("PatronumicEmpl"),
                    resultSet.getLocalDate("DateOfBirthEmpl"),
            ));
        }

        return empls;
    }

    @Override
    public void save(Employee entity) throws SQLException {
        final String query = "INSERT INTO Employee ( SurnameEmpl, NameEmpl, PatronumicEmpl, DateOfBirthEmpl) VALUES (?, ?, ?, ?)";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, entity.getSurnameEmpl());
        preparedStatement.setString(2, entity.getNameEmpl());
        preparedStatement.setString(3, entity.getPatronumicEmpl());
        preparedStatement.setLocalDate(4, entity.getDateOfBirthEmpl());

        preparedStatement.execute();
    }

    @Override
    public void delete(Employee entity) throws SQLException {
        final String query = "DELETE FROM Employee WHERE IdEmpl = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, entity.getIdEmpl());

        preparedStatement.execute();
    }

    @Override
    public void update(UserEntity entity) throws SQLException {
        final String query = "UPDATE Employee SET SurnameEmpl = ?, NameEmpl = ?, PatronumicEmpl = ?, DateOfBirthEmpl = ? WHERE IdEmpl = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, entity.getSurnameEmpl());
        preparedStatement.setString(2, entity.getNameEmpl());
        preparedStatement.setString(3, entity.getPatronumicEmpl());
        preparedStatement.setLocalDate(4, entity.getDateOfBirthEmpl());

        preparedStatement.execute();
    }
}