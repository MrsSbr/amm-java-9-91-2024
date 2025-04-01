package ru.vsu.amm.java.repos;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    Optional<T> getById(int id) throws SQLException;

    List<T> getAll() throws SQLException    ;

    void create(T object) throws SQLException;

    void update(T object) throws SQLException;

    void delete(T object) throws SQLException;
}
