package ru.vsu.amm.java.Repository.Interface;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RepositoryInterface<T> {
    Optional<T> get(int id) throws SQLException;

    List<T> getAll() throws SQLException;

    void create(T entity) throws SQLException;

    void update(T entity) throws SQLException;

    void delete(int id) throws SQLException;
}
