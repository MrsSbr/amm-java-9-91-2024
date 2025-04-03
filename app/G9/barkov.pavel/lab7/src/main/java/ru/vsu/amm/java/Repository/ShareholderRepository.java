package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.DBConnection.DBConfiguration;
import ru.vsu.amm.java.Entities.Shareholder;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShareholderRepository implements IRepository<Shareholder> {
    private final DataSource dataSource;

    public ShareholderRepository(){
        dataSource = DBConfiguration.getDataSource();
    }

    @Override
    public Optional<Shareholder> get(int id) throws SQLException {
        final String query = "SELECT id,password,fio,document,email,phone FROM shareholder WHERE id = ?";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.setInt(1,id);
        preparedStation.execute();
        var result = preparedStation.getResultSet();
        if(result.next()){
            return Optional.of(new Shareholder(
                    result.getInt(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6)
            ));
        }
        return Optional.empty();
    }

    @Override
    public List<Shareholder> getAll() throws SQLException {
        final String query = "SELECT id,password,fio,document,email,phone FROM shareholder";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.execute();
        List<Shareholder> list = new ArrayList<>();
        var result = preparedStation.getResultSet();
        while(result.next()){
            list.add(new Shareholder(
                    result.getInt(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6)
            ));
        }
        return list;
    }

    @Override
    public void create(Shareholder entity) throws SQLException {
        final String query = "INSERT INTO shareholder(password,fio,document,email,phone) VALUES (?,?,?,?,?)";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.setString(1,entity.getPassword());
        preparedStation.setString(2,entity.getFio());
        preparedStation.setString(3,entity.getDocument());
        preparedStation.setString(4,entity.getEmail());
        preparedStation.setString(5,entity.getPhone());
        preparedStation.execute();
    }

    @Override
    public void update(Shareholder entity) throws SQLException {
        final String query = "UPDATE shareholder SET password = ?,fio = ?,document = ?,email = ?,phone = ? WHERE id = ?";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.setString(1,entity.getPassword());
        preparedStation.setString(2,entity.getFio());
        preparedStation.setString(3,entity.getDocument());
        preparedStation.setString(4,entity.getEmail());
        preparedStation.setString(5,entity.getPhone());
        preparedStation.setInt(6,entity.getId());
        preparedStation.execute();
    }

    @Override
    public void delete(int id) throws SQLException {
        final String query = "DELETE FROM shareholder WHERE id = ?";
        var connection = dataSource.getConnection();
        var preparedStation = connection.prepareStatement(query);
        preparedStation.setInt(1, id);
        preparedStation.execute();
    }
}
