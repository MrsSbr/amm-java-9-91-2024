package ru.vsu.amm.java.domain.repository;

import java.sql.SQLException;

public interface Repository<T> {
    T findById(Long id) throws SQLException;
    void create(T t) throws SQLException;
    void update(T t) throws SQLException;
    void delete(T t) throws SQLException;
}
