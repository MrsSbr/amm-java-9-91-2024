package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.config.DbConfig;
import ru.vsu.amm.java.entities.Toy;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ToyRepository {

    private final DataSource dataSource;

    public ToyRepository() {
        dataSource = DbConfig.getDataSource();
    }

    public Optional<Toy> findByName(String name) throws SQLException {
        final String query = "SELECT id, name, price FROM toy WHERE name = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            return Optional.of(new Toy(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getBigDecimal("price")));
        }

        return Optional.empty();
    }

    public List<Toy> findAll() throws SQLException {
        final String query = "SELECT id, name, price FROM toy";
        List<Toy> toys = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                toys.add(new Toy(resultSet.getLong("id"),
                                 resultSet.getString("name"),
                                 resultSet.getBigDecimal("price")));

            }
        }
        return toys;
    }

    public void save(Toy toy) throws SQLException {
        final String query = "INSERT INTO toy(name, price) VALUES(?, ?)";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, toy.getName());
            preparedStatement.setBigDecimal(2, toy.getPrice());
            preparedStatement.executeUpdate();
        }
    }
}
