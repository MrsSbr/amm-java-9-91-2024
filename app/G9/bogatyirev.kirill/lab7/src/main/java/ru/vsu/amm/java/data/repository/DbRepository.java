package ru.vsu.amm.java.data.repository;

import java.sql.SQLException;

public interface DbRepository<T> {
    T findById(Long id) throws SQLException;
    void create(T t) throws SQLException;
    void update(T t) throws SQLException;
    void delete(T t) throws SQLException;
}
