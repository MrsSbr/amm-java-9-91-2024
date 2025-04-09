package ru.vsu.amm.java.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    void save(T entity) throws SQLException;
    Optional<T> findById(Long id) throws SQLException;
    List<T> findAll() throws SQLException;
    public void update(T entity) throws SQLException;
    void delete(T entity) throws SQLException;
}
