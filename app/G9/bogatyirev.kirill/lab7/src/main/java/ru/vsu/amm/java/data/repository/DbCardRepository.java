package ru.vsu.amm.java.data.repository;

import main.data.database.config.DBConfig;
import main.data.entities.DbCard;

import javax.sql.DataSource;
import java.sql.*;

public class DbCardRepository implements DbRepository<DbCard> {
    private final DataSource dataSource;

    public DbCardRepository() {
        dataSource = DBConfig.getDataSource();
    }

    @Override
    public DbCard findById(Long id) throws SQLException {
        final String query = "SELECT * FROM card WHERE id = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();


        if (resultSet.next()) {
            DbCard DBCard =  new DbCard();
            DBCard.setId(resultSet.getLong("id"));
            DBCard.setTopic(resultSet.getString("topic"));
            DBCard.setDifficulty(resultSet.getString("difficulty"));

            return DBCard;
        }


        return null;
    }

    @Override
    public void create(DbCard DBCard) throws SQLException {
        final String query = "INSERT INTO Card(topic, difficulty, player_id) VALUES (?, ?, ?)";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, DBCard.getTopic());
        preparedStatement.setString(2, DBCard.getDifficulty());
        preparedStatement.setLong(3, DBCard.getPlayerId());

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            DBCard.setId(generatedKeys.getLong(1));
        }

    }

    @Override
    public void update(DbCard DBCard) throws SQLException {

        final String query = "UPDATE Card SET topic = ?, difficulty = ?, player_id = ? WHERE id = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, DBCard.getTopic());
        preparedStatement.setString(2, DBCard.getDifficulty());
        preparedStatement.setLong(3, DBCard.getPlayerId());
        preparedStatement.setLong(4, DBCard.getId());
        preparedStatement.executeUpdate();

    }

    @Override
    public void delete(DbCard DBCard) throws SQLException {
        final String query = "DELETE FROM Card WHERE id = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, DBCard.getId());
        preparedStatement.executeUpdate();
    }

//    public Long findByWordToActions(Long wordToActionId) {
//
//
//    }
}
