package ru.vsu.amm.java.data.repository;

import ru.vsu.amm.java.data.database.config.DBConfig;
import ru.vsu.amm.java.data.entities.Player;

import javax.sql.DataSource;
import java.sql.*;

public class PlayerRepository implements DBRepository<Player> {

    private final DataSource dataSource;

    public PlayerRepository() {
        dataSource = DBConfig.getDataSource();
    }

    @Override
    public Player findById(int id) throws SQLException {

        final String query = "SELECT * FROM player WHERE id = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new Player(
                    resultSet.getLong("id"),
                    resultSet.getString("login"),
                    resultSet.getString("password"),
                    resultSet.getString("Email")
            );
        }

        return null;
    }

    @Override
    public void create(Player player) throws SQLException {

        final String query = "INSERT INTO player VALUES (?, ?, ?, ?, ?)";

        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, player.getLogin());
        statement.setString(2, player.getPassword());
        statement.setString(3, player.getEmail());
        statement.executeQuery();

        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            player.setId(generatedKeys.getLong(1));
        }
    }

    @Override
    public void update(Player player) throws SQLException {

        final String query = "UPDATE player SET login = ?, password = ?, email = ? WHERE id = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, player.getLogin());
        statement.setString(2, player.getPassword());
        statement.setString(3, player.getEmail());
        statement.setLong(4, player.getId());
        statement.executeUpdate();
    }

    @Override
    public void delete(Player player) throws SQLException {

        final String query = "DELETE FROM player WHERE id = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setLong(1, player.getId());
        statement.executeUpdate();
    }
}
