package ru.vsu.amm.java.Service.Interface;

import ru.vsu.amm.java.Repository.Entities.Stocks;

import java.sql.SQLException;
import java.util.List;

public interface StocksServiceInterface {
    List<Stocks> getAll() throws SQLException;

    List<Stocks> getAll(int userId) throws SQLException;

    Stocks getById(int id) throws SQLException;

    Stocks getByName(String name) throws SQLException;

    void sell(int userId, int stockId, int count) throws SQLException;

    void buy(int userId, int stockId, int count) throws SQLException;
}
