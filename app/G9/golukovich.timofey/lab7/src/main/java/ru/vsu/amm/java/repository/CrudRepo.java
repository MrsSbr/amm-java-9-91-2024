package ru.vsu.amm.java.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudRepo<T> {
    Optional<T> getById(int id, boolean isForUpdate) throws SQLException;

    List<T> getAll(boolean isForUpdate) throws SQLException;

    void update(T entity) throws SQLException;

    T save(T entity) throws SQLException;

    void delete(int id)  throws SQLException;
}
