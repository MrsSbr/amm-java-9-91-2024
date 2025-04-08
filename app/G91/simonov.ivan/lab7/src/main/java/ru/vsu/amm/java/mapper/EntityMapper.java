package ru.vsu.amm.java.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface EntityMapper<T> {

    T mapRowToObject(ResultSet rs) throws SQLException;

    PreparedStatement mapObjectToRow(T entity,
                                     Connection connection,
                                     String sql) throws SQLException;

}
