package ru.vsu.amm.java.Mapper.Interface;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<T> {

     T resultSetToEntity(ResultSet rs) throws SQLException;
}
