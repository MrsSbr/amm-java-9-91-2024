package ru.vsu.amm.java.repository;

import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.UUID;

public interface CRUDRepository<T> {
    @Nullable
    T getByID(UUID id);

    List<T> getAll();

    void save(T entity);

    void update(T entity);

    void delete(UUID id);
}
