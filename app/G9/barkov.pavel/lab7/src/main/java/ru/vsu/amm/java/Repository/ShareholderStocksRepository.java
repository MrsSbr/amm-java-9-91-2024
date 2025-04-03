package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.DBConnection.DBConfiguration;
import ru.vsu.amm.java.Entities.ShareholderStocks;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShareholderStocksRepository implements ICRepository<ShareholderStocks> {
    private final DataSource dataSource;

    public ShareholderStocksRepository() {
        dataSource = DBConfiguration.getDataSource();
    }

    @Override
    public Optional<ShareholderStocks> get(int first_id, int second_id) throws SQLException {
        final String query = "SELECT shareholder_id,stock_id,count FROM shareholder_stocks " +
                "WHERE shareholder_id = ? AND stock_id = ?";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.setInt(1, first_id);
        preparedStation.setInt(2, second_id);
        preparedStation.execute();
        var result = preparedStation.getResultSet();
        if (result.next()) {
            return Optional.of(new ShareholderStocks(
                    result.getInt(1),
                    result.getInt(2),
                    result.getInt(3)
            ));
        }
        return Optional.empty();
    }

    @Override
    public List<ShareholderStocks> getAll() throws SQLException {
        final String query = "SELECT shareholder_id,stock_id,count FROM shareholder_stocks";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.execute();
        List<ShareholderStocks> list = new ArrayList<>();
        var result = preparedStation.getResultSet();
        while (result.next()) {
            list.add(new ShareholderStocks(
                    result.getInt(1),
                    result.getInt(2),
                    result.getInt(3)
            ));
        }
        return list;
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
    public void delete(int first_id, int second_id) throws SQLException {
        final String query = "DELETE FROM shareholder_stocks WHERE shareholder_id = ? AND stock_id = ?";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.setInt(1, first_id);
        preparedStation.setInt(2, second_id);
        preparedStation.execute();
    }
}
