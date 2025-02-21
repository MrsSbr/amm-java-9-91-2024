package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entity.UserEntity;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UserRepository implements CrudRepository<UserEntity> {

    private final DataSource dataSource;

    public UserRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<UserEntity> findAll() {
        return null;
    }

    @Override
    public void update(UserEntity entity) {

    }

    @Override
    public void save(UserEntity entity) {

    }

    @Override
    public void delete(UserEntity entity) {

    }
}
