package ru.vsu.amm.java.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DatabaseRepository<T> {
    Optional<T> findById(Long id) throws SQLException;

    Optional<T> findByUserName(Long id) throws SQLException;

    List<T> findAll() throws SQLException;

    void update(T entity) throws SQLException;

    void save(T entity) throws SQLException;

    void delete(T entity) throws SQLException;
}