package ru.vsu.amm.java.data.repository;


import ru.vsu.amm.java.data.database.config.DBConfig;
import ru.vsu.amm.java.data.entities.Card;
import ru.vsu.amm.java.data.entities.utils.Difficulty;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CardRepository implements DBRepository<Card>{
    private final DataSource dataSource;
    private final WordToActionRepository wordToActionRepository;

    public CardRepository() {
        dataSource = DBConfig.getDataSource();
        wordToActionRepository = new WordToActionRepository();
    }

    @Override
    public Card findById(int id) throws SQLException {
        final String query = "SELECT * FROM card WHERE id = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();


        if (resultSet.next()) {
            List<Long> wordsToActions = wordToActionRepository.findByCardId(id);

            Card card =  new Card();
            card.setId(resultSet.getLong("id"));
            card.setTopic(resultSet.getString("topic"));
            card.setDifficulty(Difficulty.valueOf(resultSet.getString("difficulty")));
            card.setWordsToActions(wordsToActions);

            return card;
        }


        return null;
    }

    @Override
    public void create(Card card) {

    }

    @Override
    public void update(Card card) {

    }

    @Override
    public void delete(Card card) {

    }
}
