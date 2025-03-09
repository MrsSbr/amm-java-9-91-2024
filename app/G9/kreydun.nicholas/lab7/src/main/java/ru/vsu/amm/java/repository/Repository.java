package ru.vsu.amm.java.repository;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface Repository<T> {
    @Nullable
    T getById(UUID id);

    List<T> getAll();

    UUID create(T entity);

    boolean update(T entity);

    boolean delete(UUID id);
}
