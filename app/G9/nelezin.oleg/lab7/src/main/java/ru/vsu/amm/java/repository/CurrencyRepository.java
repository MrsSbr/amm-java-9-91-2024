package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entity.Currency;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyRepository implements CrudRepository<Currency> {

    private final DataSource dataSource;

    public CurrencyRepository() {
        dataSource = DatabaseConfiguration.getDataSourceForService();
    }

    @Override
    public Optional<Currency> findById(Long id) throws SQLException {
        final String query = "SELECT id, code, name, sign FROM currency WHERE id = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            return Optional.of(new Currency(
                    resultSet.getLong("id"),
                    resultSet.getString("code"),
                    resultSet.getString("name"),
                    resultSet.getString("sign")
            ));
        }

        return Optional.empty();
    }

    @Override
    public List<Currency> findAll() throws SQLException {
        final String query = "SELECT id, code, name, sign FROM currency";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();
        List<Currency> currencies = new ArrayList<>();
        while (resultSet.next()) {
            currencies.add(new Currency(
                    resultSet.getLong("id"),
                    resultSet.getString("code"),
                    resultSet.getString("name"),
                    resultSet.getString("sign")
            ));
        }
        return currencies;
    }

    public Optional<Currency> findByCode(String code) throws SQLException {
        final String query = "SELECT id, code, name, sign FROM currency WHERE code = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, code);
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            return Optional.of(new Currency(
                    resultSet.getLong("id"),
                    resultSet.getString("code"),
                    resultSet.getString("name"),
                    resultSet.getString("sign")
                    ));
        }
        return Optional.empty();
    }

    @Override
    public Long save(Currency entity) {
        return null;
    }
}
