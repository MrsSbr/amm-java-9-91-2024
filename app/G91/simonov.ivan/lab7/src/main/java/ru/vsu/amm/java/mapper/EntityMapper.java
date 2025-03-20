package ru.vsu.amm.java.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface EntityMapper<T> {

    T mapRowToObject(ResultSet rs) throws SQLException;

    void mapObjectToRow(PreparedStatement stmt) throws SQLException;

}
