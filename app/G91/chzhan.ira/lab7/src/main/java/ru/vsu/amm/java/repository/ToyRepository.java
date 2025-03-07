package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.config.DbConfig;
import ru.vsu.amm.java.entities.Toy;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
