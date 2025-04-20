package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.DBConnection.DBConfiguration;
import ru.vsu.amm.java.Repository.Convertors.ResultSetToShareholderStocks;
import ru.vsu.amm.java.Repository.Convertors.ResultSetToStocks;
import ru.vsu.amm.java.Repository.Entities.ShareholderStocks;
import ru.vsu.amm.java.Repository.Entities.Stocks;
import ru.vsu.amm.java.Repository.Interface.SRepositoryInterface;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StocksRepository implements SRepositoryInterface {
    private final DataSource dataSource;

    public StocksRepository() {
        dataSource = DBConfiguration.getDataSource();
    }

    @Override
    public Optional<Stocks> get(int stocksId) throws SQLException {
        final String query = "SELECT id,price,count,name,dividends FROM stocks WHERE id = ?";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.setInt(1, stocksId);
        preparedStation.execute();
        var result = preparedStation.getResultSet();
        if (result.next()) {
            return Optional.of(ResultSetToStocks.convert(result));
        }
        return Optional.empty();
    }

    @Override
    public Optional<ShareholderStocks> get(int userId, int stocksId) throws SQLException {
        final String query = """
                SELECT shareholder_id,stock_id,count
                FROM shareholder_stocks 
                WHERE shareholder_id = ? AND stock_id = ?
                """;
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.setInt(1, userId);
        preparedStation.setInt(2, stocksId);
        preparedStation.execute();
        var result = preparedStation.getResultSet();
        if (result.next()) {
            return Optional.of(ResultSetToShareholderStocks.convert(result));
        }
        return Optional.empty();
    }

    public Optional<Stocks> getByName(String stocksName) throws SQLException {
        final String query = "SELECT id,price,count,name,dividends FROM stocks WHERE name = ?";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.setString(1, stocksName);
        preparedStation.execute();
        var result = preparedStation.getResultSet();
        if (result.next()) {
            return Optional.of(ResultSetToStocks.convert(result));
        }
        return Optional.empty();
    }

    @Override
    public List<Stocks> getAll(int limit, int offset) throws SQLException {
        final String query = "SELECT id,price,count,name,dividends FROM stocks LIMIT ? OFFSET ?";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.setInt(1, limit);
        preparedStation.setInt(2, offset);
        preparedStation.execute();
        List<Stocks> list = new ArrayList<>();
        var result = preparedStation.getResultSet();
        while (result.next()) {
            list.add(ResultSetToStocks.convert(result));
        }
        return list;
    }

    @Override
    public List<Stocks> getAll(int userId, int limit, int offset) throws SQLException {
        final String query = """
                SELECT s.id,s.price,ss.count,s.name,s.dividends
                FROM stocks s JOIN shareholder_stocks ss ON s.id = ss.stock_id
                WHERE ss.shareholder_id = ?
                LIMIT ? OFFSET ?
                """;
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.setInt(1, userId);
        preparedStation.setInt(2, limit);
        preparedStation.setInt(3, offset);
        preparedStation.execute();
        List<Stocks> list = new ArrayList<>();
        var result = preparedStation.getResultSet();
        while (result.next()) {
            list.add(ResultSetToStocks.convert(result));
        }
        return list;
    }

    @Override
    public void create(Stocks entity) throws SQLException {
        final String query = "INSERT INTO stocks(price,count,name,dividends) VALUES (?,?,?,?)";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.setDouble(1, entity.getPrice());
        preparedStation.setInt(2, entity.getCount());
        preparedStation.setString(3, entity.getName());
        preparedStation.setDouble(4, entity.getDividends());
        preparedStation.execute();
    }

    @Override
    public void update(Stocks entity) throws SQLException {
        final String query = "UPDATE stocks SET price = ?,count = ?,name = ?,dividends = ? WHERE id = ?";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.setDouble(1, entity.getPrice());
        preparedStation.setInt(2, entity.getCount());
        preparedStation.setString(3, entity.getName());
        preparedStation.setDouble(4, entity.getDividends());
        preparedStation.setInt(5, entity.getId());
        preparedStation.execute();
    }

    @Override
    public void delete(int stocksId) throws SQLException {
        final String query = "DELETE FROM stocks WHERE id = ?";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.setInt(1, stocksId);
        preparedStation.execute();
    }

    @Override
    public void create(ShareholderStocks entity) throws SQLException {
        final String query = "INSERT INTO shareholder_stocks(shareholder_id,stock_id,count) VALUES (?,?,?)";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.setInt(1, entity.getShareholderId());
        preparedStation.setInt(2, entity.getStocksId());
        preparedStation.setInt(3, entity.getCount());
        preparedStation.execute();
    }

    @Override
    public void update(ShareholderStocks entity) throws SQLException {
        final String query = "UPDATE shareholder_stocks SET count = ? WHERE shareholder_id = ? AND stock_id = ?";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.setInt(1, entity.getCount());
        preparedStation.setInt(2, entity.getShareholderId());
        preparedStation.setInt(3, entity.getStocksId());
        preparedStation.execute();
    }

    @Override
    public void delete(int userId, int stocksId) throws SQLException {
        final String query = "DELETE FROM shareholder_stocks WHERE shareholder_id = ? AND stock_id = ?";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.setInt(1, userId);
        preparedStation.setInt(2, stocksId);
        preparedStation.execute();
    }

    @Override
    public int count() throws SQLException {
        final String query = "SELECT COUNT(*) FROM stocks";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.execute();
        var result = preparedStation.getResultSet();
        if (!result.next())
            return 0;
        return result.getInt(1);
    }

    @Override
    public int count(int userId) throws SQLException {
        final String query = "SELECT COUNT(*) FROM shareholder_stocks WHERE shareholder_id = ?";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.setInt(1, userId);
        preparedStation.execute();
        var result = preparedStation.getResultSet();
        if (!result.next())
            return 0;
        return result.getInt(1);
    }
}
