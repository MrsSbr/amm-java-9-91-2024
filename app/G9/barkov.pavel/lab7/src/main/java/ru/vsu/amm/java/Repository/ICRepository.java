package ru.vsu.amm.java.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ICRepository<T> {
    Optional<T> get(int first_id, int second_id) throws SQLException;

    List<T> getAll() throws SQLException;

    void create(T entity) throws SQLException;

    void update(T entity) throws SQLException;

    void delete(int first_id, int second_id) throws SQLException;
}
