package ru.vsu.amm.java.repository;

import java.util.List;

public interface CrudRepository<T> {
    T findById(long id);

    List<T> findAll();

    void create(T entity);

    void update(T entity);

    void delete(T entity);
}
