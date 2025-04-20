package ru.vsu.amm.java.Repository.Interface;

import ru.vsu.amm.java.Repository.Entities.Shareholder;
import ru.vsu.amm.java.Repository.Entities.ShareholderStocks;
import ru.vsu.amm.java.Repository.Entities.Stocks;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface SRepositoryInterface {
    Optional<Stocks> get(int stocksId) throws SQLException;

    Optional<ShareholderStocks> get(int userId, int stocksId) throws SQLException;

    List<Stocks> getAll(int limit,int offset) throws SQLException;

    List<Stocks> getAll(int userId,int limit,int offset) throws SQLException;

    void create(Stocks entity) throws SQLException;

    void update(Stocks entity) throws SQLException;

    void create(ShareholderStocks entity) throws SQLException;

    void update(ShareholderStocks entity) throws SQLException;

    void delete(int stocksId) throws SQLException;

    void delete(int userId, int stocksId) throws SQLException;

    int count() throws SQLException;

    int count(int userId) throws SQLException;
}
