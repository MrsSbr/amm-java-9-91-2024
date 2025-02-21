package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;

import javax.sql.DataSource;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

public class CurrencyRepository implements CrudRepository<Currency> {

    private final DataSource dataSource;

    public CurrencyRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<Currency> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Currency> findAll() {
        return null;
    }

    @Override
    public void update(Currency entity) {

    }

    @Override
    public void save(Currency entity) {

    }

    @Override
    public void delete(Currency entity) {

    }
}
