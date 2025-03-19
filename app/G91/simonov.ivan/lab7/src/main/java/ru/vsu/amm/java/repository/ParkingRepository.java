package ru.vsu.amm.java.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ParkingRepository<T> {

    Optional<T> getById(int id) throws SQLException;

    List<T> getAll() throws SQLException;

    void save(T entity);

    void update(T entity);

    void delete(T entity);
}
