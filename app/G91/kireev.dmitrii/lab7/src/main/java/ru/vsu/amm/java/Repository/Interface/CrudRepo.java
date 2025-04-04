package ru.vsu.amm.java.Repository.Interface;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudRepo<T> {

    Optional<T> findById(Long id) throws SQLException;

    List<T> findAll() throws SQLException;

    void save(T entity) throws SQLException;

    void update(T entity) throws SQLException;

    void delete(Long id) throws SQLException;

}
