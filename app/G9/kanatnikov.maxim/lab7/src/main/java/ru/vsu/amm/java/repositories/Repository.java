package ru.vsu.amm.java.repositories;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    Optional<T> findById(Long id) throws SQLException;

    List<T> findAll() throws SQLException;

    void update(T entity) throws SQLException;

    void save(T entity) throws SQLException;

    void delete(T entity) throws SQLException;
}
