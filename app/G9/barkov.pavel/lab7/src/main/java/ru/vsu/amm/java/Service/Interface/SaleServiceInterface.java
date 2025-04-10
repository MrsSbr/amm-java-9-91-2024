package ru.vsu.amm.java.Service.Interface;

import ru.vsu.amm.java.Repository.Entities.ShareholderStocks;

import java.sql.SQLException;
import java.util.List;

public interface SaleServiceInterface {
    List<ShareholderStocks> getAll() throws SQLException;
    void Sell(int userId,int stockId,int count) throws SQLException;
    void Buy(int userId,int stockId,int count) throws SQLException;

}
