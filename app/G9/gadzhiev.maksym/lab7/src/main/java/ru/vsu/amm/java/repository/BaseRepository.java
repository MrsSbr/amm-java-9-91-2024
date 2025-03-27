package ru.vsu.amm.java.repository;

import java.sql.SQLException;
import java.util.Optional;

public interface BaseRepository<T> {
    Optional<T> findById(Long id) throws SQLException;
    void update(T entity) throws SQLException;
    void save(T entity) throws SQLException;
    void delete(Long id) throws SQLException;

}
