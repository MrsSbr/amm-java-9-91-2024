package ru.vsu.amm.java.data.repository;


import ru.vsu.amm.java.data.database.config.DBConfig;
import ru.vsu.amm.java.data.entities.Action;

import javax.sql.DataSource;
import java.sql.*;

public class ActionRepository implements DBRepository<Action>{
    private final DataSource dataSource;

    ActionRepository() {
        dataSource = DBConfig.getDataSource();
    }

    @Override
    public Action findById(int id) throws SQLException {

        final String query = "SELECT * FROM action WHERE id = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return new Action(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("points")
            );
        }

        return null;
    }

    @Override
    public void create(Action action) throws SQLException {

        final String query = "INSERT INTO action (name, points) VALUES (?, ?)";

        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, action.getName());
        statement.setInt(2, action.getPoints());
        statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            action.setId(generatedKeys.getLong(1));
        }

    }

    @Override
    public void update(Action action) throws SQLException{

        final String query = "UPDATE action SET name = ?, points = ? WHERE id = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, action.getName());
        statement.setInt(2, action.getPoints());
        statement.setLong(3, action.getId());
        statement.executeUpdate();

    }

    @Override
    public void delete(Action action) throws SQLException {

        final String query = "DELETE FROM action WHERE id = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setLong(1, action.getId());
        statement.executeUpdate();
    }
}
