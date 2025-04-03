package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.Repository.Convertors.ResultSetToStocks;
import ru.vsu.amm.java.DBConnection.DBConfiguration;
import ru.vsu.amm.java.Repository.Entities.Stocks;
import ru.vsu.amm.java.Repository.Interface.RepositoryInterface;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StocksRepository implements RepositoryInterface<Stocks> {

    private final DataSource dataSource;

    public StocksRepository() {
        dataSource = DBConfiguration.getDataSource();
    }
    @Override
    public Optional<Stocks> get(int id) throws SQLException {
        final String query = "SELECT id,price,count,name,edividends FROM stocks WHERE id = ?";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.setInt(1,id);
        preparedStation.execute();
        var result = preparedStation.getResultSet();
        if(result.next()){
            return Optional.of(ResultSetToStocks.Convert(result));
        }
        return Optional.empty();
    }

    public Optional<Stocks> getByName(String name) throws SQLException {
        final String query = "SELECT id,price,count,name,edividends FROM stocks WHERE name = ?";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.setString(1,name);
        preparedStation.execute();
        var result = preparedStation.getResultSet();
        if(result.next()){
            return Optional.of(ResultSetToStocks.Convert(result));
        }
        return Optional.empty();
    }

    @Override
    public List<Stocks> getAll() throws SQLException {
        final String query = "SELECT id,price,count,name,edividends FROM stocks";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.execute();
        List<Stocks> list = new ArrayList<>();
        var result = preparedStation.getResultSet();
        while(result.next()){
            list.add(ResultSetToStocks.Convert(result));
        }
        return list;
    }

    @Override
    public void create(Stocks entity) throws SQLException {
        final String query = "INSERT INTO stocks(price,count,name,dividends) VALUES (?,?,?,?)";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.setDouble(1,entity.getPrice());
        preparedStation.setInt(2,entity.getCount());
        preparedStation.setString(3,entity.getName());
        preparedStation.setDouble(4,entity.getDividends());
        preparedStation.execute();
    }

    @Override
    public void update(Stocks entity) throws SQLException {
        final String query = "UPDATE stocks SET price = ?,count = ?,name = ?,dividends = ? WHERE id = ?";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.setDouble(1,entity.getPrice());
        preparedStation.setInt(2,entity.getCount());
        preparedStation.setString(3,entity.getName());
        preparedStation.setDouble(4,entity.getDividends());
        preparedStation.setInt(5,entity.getId());
        preparedStation.execute();
    }

    @Override
    public void delete(int id) throws SQLException {
        final String query = "DELETE FROM stocks WHERE id = ?";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.setInt(1,id);
        preparedStation.execute();
    }
}
