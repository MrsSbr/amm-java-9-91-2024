package ru.vsu.amm.java.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ParkingRepository<T> {

    Optional<T> getById(int id) throws SQLException;

    List<T> getAll() throws SQLException;

    int save(T entity) throws SQLException;

    void update(T entity) throws SQLException;

    void delete(T entity) throws SQLException;
}
