package ru.vsu.amm.java.Repository.Convertors;

import ru.vsu.amm.java.Repository.Entities.Stocks;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToStocks {
    public static Stocks convert(ResultSet result) throws SQLException {
        return new Stocks(
                result.getInt(1),
                result.getDouble(2),
                result.getInt(3),
                result.getString(4),
                result.getDouble(5));
    }
}
