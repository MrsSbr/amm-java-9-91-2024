package ru.vsu.amm.java.data.repository;

import ru.vsu.amm.java.data.database.config.DBConfig;
import ru.vsu.amm.java.data.entities.WordToAction;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WordToActionRepository implements DBRepository<WordToAction> {

    private final DataSource datasource;

    public WordToActionRepository() {
        datasource = DBConfig.getDataSource();
    }

    @Override
    public WordToAction findById(int id) throws SQLException {

        final String query = "SELECT * FROM WordToAction WHERE id = ?";

        Connection connection = datasource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return new WordToAction(
                    resultSet.getLong("id"),
                    resultSet.getString("word"),
                    resultSet.getLong("action_id"),
                    resultSet.getLong("card_id")
            );
        }

        return null;
    }

    @Override
    public void create(WordToAction wordToAction) throws SQLException {

        final String query = "INSERT INTO word_to_action (word, action) VALUES (?, ?, ?)";

        Connection connection = datasource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, wordToAction.getWord());
        preparedStatement.setLong(2, wordToAction.getActionId());
        preparedStatement.setLong(3, wordToAction.getCardId());
        preparedStatement.executeUpdate();

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            wordToAction.setId(generatedKeys.getLong(1));
        }

    }

    @Override
    public void update(WordToAction wordToAction) throws SQLException {

        final String query = "UPDATE word_to_action SET word = ?, action_id = ?, card_id = ? WHERE id = ?";

        Connection connection = datasource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, wordToAction.getWord());
        preparedStatement.setLong(2, wordToAction.getActionId());
        preparedStatement.setLong(3, wordToAction.getCardId());
        preparedStatement.setLong(4, wordToAction.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(WordToAction wordToAction) throws SQLException {

        final String query = "DELETE FROM word_to_action WHERE id = ?";

        Connection connection = datasource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, wordToAction.getId());
        preparedStatement.executeUpdate();
    }

    public List<Long> findByCardId(int cardId) throws SQLException {
        final String query = "SELECT id FROM word_to_action WHERE card_id = ?";
        List<Long> result = new ArrayList<>();

        Connection connection = datasource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, cardId);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            result.add(resultSet.getLong("id"));
        }

        return result;
    }
}
