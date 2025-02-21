package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entity.ExchangeRate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class ExchangeRateRepository implements CrudRepository<ExchangeRate> {
    private final DataSource dataSource;

    public ExchangeRateRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<ExchangeRate> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<ExchangeRate> findAll() {
        return null;
    }

    @Override
    public void update(ExchangeRate entity) {

    }

    @Override
    public void save(ExchangeRate entity) {

    }

    @Override
    public void delete(ExchangeRate entity) {

    }
}
