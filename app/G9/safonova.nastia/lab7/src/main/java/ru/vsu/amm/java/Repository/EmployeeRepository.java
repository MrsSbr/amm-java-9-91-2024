package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.Configuration.DbConfiguration;
import ru.vsu.amm.java.Entities.Employee;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeRepository implements DbRepository<Employee> {

    private final DataSource dataSource;

    public EmployeeRepository() {
        dataSource = DbConfiguration.getDataSource();
    }

    @Override
    public Optional<Employee> findById(Long id) throws SQLException {
        final String query = "SELECT IdEmpl, LoginEmpl, PasswordEmpl, SurnameEmpl, NameEmpl, PatronumicEmpl, DateOfBirthEmpl FROM Employee WHERE idempl = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);

        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();

        if (resultSet.next()) {
            return Optional.of(new Employee(
                    resultSet.getLong("IdEmpl"),
                    resultSet.getString("LoginEmpl"),
                    resultSet.getString("PasswordEmpl"),
                    resultSet.getString("SurnameEmpl"),
                    resultSet.getString("NameEmpl"),
                    resultSet.getString("PatronumicEmpl"),
                    resultSet.getDate("DateOfBirthEmpl").toLocalDate()
            ));
        }

        return Optional.empty();
    }

    @Override
    public List<Employee> findAll() throws SQLException {
        final String query = "SELECT IdEmpl, LoginEmpl, PasswordEmpl, SurnameEmpl, NameEmpl, PatronumicEmpl, DateOfBirthEmpl FROM Employee";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Employee> empls = new ArrayList<>();
        while (resultSet.next()) {
            empls.add(new Employee(
                    resultSet.getLong("IdEmpl"),
                    resultSet.getString("LoginEmpl"),
                    resultSet.getString("PasswordEmpl"),
                    resultSet.getString("SurnameEmpl"),
                    resultSet.getString("NameEmpl"),
                    resultSet.getString("PatronumicEmpl"),
                    resultSet.getDate("DateOfBirthEmpl").toLocalDate()
            ));
        }

        return empls;
    }

    @Override
    public void save(Employee entity) throws SQLException {
        final String query = "INSERT INTO Employee (LoginEmpl, PasswordEmpl, SurnameEmpl, NameEmpl, PatronumicEmpl, DateOfBirthEmpl) VALUES (?, ?, ?, ?, ?, ?) RETURNING IdEmpl";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getSurnameEmpl());
            preparedStatement.setString(4, entity.getNameEmpl());
            preparedStatement.setString(5, entity.getPatronumicEmpl());
            preparedStatement.setDate(6, Date.valueOf(entity.getDateOfBirthEmpl()));

            preparedStatement.execute();

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    entity.setIdEmpl(rs.getLong(1));
                }
            }
        }
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
    public void update(Employee entity) throws SQLException {
        final String query = "UPDATE Employee SET SurnameEmpl = ?, NameEmpl = ?, PatronumicEmpl = ?, DateOfBirthEmpl = ? WHERE IdEmpl = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, entity.getSurnameEmpl());
        preparedStatement.setString(2, entity.getNameEmpl());
        preparedStatement.setString(3, entity.getPatronumicEmpl());
        preparedStatement.setDate(4, Date.valueOf(entity.getDateOfBirthEmpl()));
        preparedStatement.setLong(5, entity.getIdEmpl());

        preparedStatement.execute();
    }

    public Optional<Employee> findByLogin(String login) throws SQLException {
        final String query = "SELECT IdEmpl, LoginEmpl, PasswordEmpl, SurnameEmpl, NameEmpl, PatronumicEmpl, DateOfBirthEmpl FROM Employee WHERE LoginEmpl = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, login);

        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();

        if (resultSet.next()) {
            return Optional.of(new Employee(
                    resultSet.getLong("IdEmpl"),
                    resultSet.getString("LoginEmpl"),
                    resultSet.getString("PasswordEmpl"),
                    resultSet.getString("SurnameEmpl"),
                    resultSet.getString("NameEmpl"),
                    resultSet.getString("PatronumicEmpl"),
                    resultSet.getDate("DateOfBirthEmpl").toLocalDate()
            ));
        }

        return Optional.empty();
    }
}