package ru.vsu.amm.java.Mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<T> {
    T mapRowToObject(ResultSet resultSet) throws SQLException;

    PreparedStatement mapObjectToRow(T entity,
                                     Connection connection,
                                     String sql) throws SQLException;
}
