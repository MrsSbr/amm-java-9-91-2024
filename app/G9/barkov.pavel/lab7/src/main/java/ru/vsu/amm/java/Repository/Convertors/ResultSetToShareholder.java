package ru.vsu.amm.java.Repository.Convertors;

import ru.vsu.amm.java.Repository.Entities.Shareholder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToShareholder {
    public static Shareholder convert(ResultSet result) throws SQLException{
        return new Shareholder(
                result.getInt(1),
                result.getString(2),
                result.getString(3),
                result.getString(4),
                result.getString(5),
                result.getString(6));
    }
}
