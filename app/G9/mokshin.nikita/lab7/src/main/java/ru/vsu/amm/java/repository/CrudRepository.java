package ru.vsu.amm.java.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    public Optional<T> findById(long id);
    public List<T> findAll();
    public void save(T t);
    public void delete(T t);
}
