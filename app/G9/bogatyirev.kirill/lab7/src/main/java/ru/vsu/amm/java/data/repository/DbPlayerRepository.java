package ru.vsu.amm.java.data.repository;

import main.data.database.config.DBConfig;
import main.data.entities.DbPlayer;

import javax.sql.DataSource;
import java.sql.*;

public class DbPlayerRepository implements DbRepository<DbPlayer> {

    private final DataSource dataSource;

    public DbPlayerRepository() {
        dataSource = DBConfig.getDataSource();
    }

    @Override
    public DbPlayer findById(Long id) throws SQLException {

        final String query = "SELECT * FROM player WHERE id = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new DbPlayer(
                    resultSet.getLong("id"),
                    resultSet.getString("login"),
                    resultSet.getString("password"),
                    resultSet.getString("email")
            );
        }

        return null;
    }

    @Override
    public void create(DbPlayer DBPlayer) throws SQLException {

        final String query = "INSERT INTO player(login, password, email) VALUES (?, ?, ?)";

        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, DBPlayer.getLogin());
        statement.setString(2, DBPlayer.getPassword());
        statement.setString(3, DBPlayer.getEmail());
        statement.executeQuery();
    }

    @Override
    public void update(DbPlayer DBPlayer) throws SQLException {

        final String query = "UPDATE player SET login = ?, password = ?, email = ? WHERE id = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, DBPlayer.getLogin());
        statement.setString(2, DBPlayer.getPassword());
        statement.setString(3, DBPlayer.getEmail());
        statement.setLong(4, DBPlayer.getId());
        statement.executeUpdate();
    }

    @Override
    public void delete(DbPlayer DBPlayer) throws SQLException {

        final String query = "DELETE FROM player WHERE id = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setLong(1, DBPlayer.getId());
        statement.executeUpdate();
    }

    public DbPlayer findByLogin(String login) throws SQLException {

        final String query = "SELECT id, password, email FROM player WHERE login = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new DbPlayer(
                    resultSet.getLong("id"),
                    login,
                    resultSet.getString("password"),
                    resultSet.getString("Email")
            );
        }

        return null;
    }
}
