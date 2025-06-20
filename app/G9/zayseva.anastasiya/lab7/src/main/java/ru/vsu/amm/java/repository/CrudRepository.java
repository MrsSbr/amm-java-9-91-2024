package ru.vsu.amm.java.repository;

import java.util.List;

public interface CrudRepository<T>{
    T getById(long id);

    List<T> getAll();

    void save(T entity);

    void update(T entity);

    void delete(long id);
}
