package ru.vsu.amm.java.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    Optional<T> findById(Long id);

    List<T> findAll();

    void update(T entity);

    void save(T entity);

    void delete(T entity);
}
