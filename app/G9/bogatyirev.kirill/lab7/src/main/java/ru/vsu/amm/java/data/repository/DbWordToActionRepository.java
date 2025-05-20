package ru.vsu.amm.java.data.repository;


import ru.vsu.amm.java.data.database.config.DBConfig;
import ru.vsu.amm.java.data.entities.DbWordToAction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DbWordToActionRepository implements DbRepository<DbWordToAction> {

    private final DataSource datasource;

    private static final Logger log = Logger.getLogger(DbWordToActionRepository.class.getName());


    public DbWordToActionRepository() {
        datasource = DBConfig.getDataSource();
    }
    @Override
    public DbWordToAction findById(Long id) throws SQLException {

        final String query = "SELECT id, word, action_id, card_id FROM WordToAction WHERE id = ?";

        Connection connection = datasource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return new DbWordToAction(
                    resultSet.getLong("id"),
                    resultSet.getString("word"),
                    resultSet.getLong("action_id"),
                    resultSet.getLong("card_id")
            );
        }

        return null;
    }

    @Override
    public void create(DbWordToAction dbWordToAction) throws SQLException {

        final String query = "INSERT INTO word_to_action (word, action_id, card_id) VALUES (?, ?, ?)";

        Connection connection = datasource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS);


        preparedStatement.setString(1, dbWordToAction.getWord());
        preparedStatement.setLong(2, dbWordToAction.getActionId());
        preparedStatement.setLong(3, dbWordToAction.getCardId());//
        preparedStatement.executeUpdate();

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            dbWordToAction.setId(generatedKeys.getLong(1));
        }

        log.info("DbWordToAction created: " + dbWordToAction.getWord());
    }

    @Override
    public void update(DbWordToAction dbWordToAction) throws SQLException {

        final String query = "UPDATE word_to_action SET word = ?, action_id = ? WHERE id = ?";

        Connection connection = datasource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, dbWordToAction.getWord());
        preparedStatement.setLong(2, dbWordToAction.getActionId());
        preparedStatement.setLong(3, dbWordToAction.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(DbWordToAction dbWordToAction) throws SQLException {

        final String query = "DELETE FROM word_to_action WHERE id = ?";

        Connection connection = datasource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, dbWordToAction.getId());
        preparedStatement.executeUpdate();
    }

    public List<DbWordToAction> findByCardId(Long cardId) throws SQLException {
        final String query = "SELECT id FROM word_to_action WHERE card_id = ?";
        List<DbWordToAction> result = new ArrayList<>();

        Connection connection = datasource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, cardId);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Long wordToActionId = resultSet.getLong("id");
            DbWordToAction newDbWordToAction = findById(wordToActionId);
            result.add(newDbWordToAction);
        }

        return result;
    }
}
