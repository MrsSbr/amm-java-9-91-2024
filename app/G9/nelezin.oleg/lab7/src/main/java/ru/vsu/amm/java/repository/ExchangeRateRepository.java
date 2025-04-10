package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entity.ExchangeRate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ExchangeRateRepository implements CrudRepository<ExchangeRate> {
    private final DataSource dataSource;

    public ExchangeRateRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<ExchangeRate> findById(Long id) throws SQLException {
        final String query = """
                SELECT 
                    id, 
                    base_currency_id, 
                    target_currency_id, 
                    rate 
                FROM exchange_rate WHERE id = ?
                """;
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            return Optional.of(new ExchangeRate(
               resultSet.getLong("id"),
               resultSet.getLong("base_currency_id"),
               resultSet.getLong("target_currency_id"),
               resultSet.getBigDecimal("rate")
            ));
        }

        return Optional.empty();
    }

    @Override
    public List<ExchangeRate> findAll() {
        return null;
    }

    public Optional<ExchangeRate> findByCodes(Long baseCurrencyId, Long targetCurrencyId) throws SQLException {
        final String query = """
                SELECT 
                    id, 
                    base_currency_id, 
                    target_currency_id, 
                    rate
                FROM exchange_rate WHERE base_currency_id = ? AND target_currency_id = ?
                """;
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, baseCurrencyId);
        preparedStatement.setLong(2, targetCurrencyId);
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            return Optional.of(new ExchangeRate(
                    resultSet.getLong("id"),
                    resultSet.getLong("base_currency_id"),
                    resultSet.getLong("target_currency_id"),
                    resultSet.getBigDecimal("rate")
            ));
        }
        return Optional.empty();
    }

    @Override
    public Long save(ExchangeRate entity) {
        return null;
    }
}
