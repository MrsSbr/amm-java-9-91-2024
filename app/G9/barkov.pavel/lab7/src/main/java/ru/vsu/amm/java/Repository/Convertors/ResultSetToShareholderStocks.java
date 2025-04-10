package ru.vsu.amm.java.Repository.Convertors;

import ru.vsu.amm.java.Repository.Entities.ShareholderStocks;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToShareholderStocks {
    public static ShareholderStocks convert(ResultSet result) throws SQLException {
        return new ShareholderStocks(
                result.getInt(1),
                result.getInt(2),
                result.getInt(3));
    }
}
