package ru.vsu.amm.java.repo;

import ru.vsu.amm.java.entity.EarnedAchievement;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    Optional<T> findById(final Long id) throws SQLException;

    List<T> findAll() throws SQLException;

    void update(final T object) throws SQLException;

    //void insert(final T object) throws SQLException;
    void delete(final T object);

    boolean save(final T object) throws SQLException;
}
