package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.entities.HotelEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IRepo<T> {
    public Optional<T> getById(int id) throws SQLException;

    List<T> getAll() throws SQLException;

    void update(T entity) throws SQLException;

    void save(T entity) throws SQLException;

    void delete(int id)  throws SQLException;
}
