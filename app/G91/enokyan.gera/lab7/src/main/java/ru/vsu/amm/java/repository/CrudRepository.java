package ru.vsu.amm.java.repository;

import java.sql.Connection;
import java.util.List;

public interface CrudRepository<T> {
    T findById(long id);

    List<T> findAll();

    long create(T entity);

    void update(T entity);

    void delete(Connection connection, T entity);
}
