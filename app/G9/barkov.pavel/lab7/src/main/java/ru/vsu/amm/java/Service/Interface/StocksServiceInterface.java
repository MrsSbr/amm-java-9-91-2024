package ru.vsu.amm.java.Service.Interface;

import ru.vsu.amm.java.Repository.Entities.Stocks;

import java.sql.SQLException;
import java.util.List;

public interface StocksServiceInterface {
    List<Stocks> getAll() throws SQLException;
    Stocks getById(int id) throws SQLException;
    Stocks getByName(String name) throws SQLException;
    void update(int id,int change) throws SQLException;;
}
