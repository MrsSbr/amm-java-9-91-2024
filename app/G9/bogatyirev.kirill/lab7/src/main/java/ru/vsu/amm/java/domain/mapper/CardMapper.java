package ru.vsu.amm.java.domain.mapper;


import ru.vsu.amm.java.data.entities.DbCard;
import ru.vsu.amm.java.data.entities.DbWordToAction;
import ru.vsu.amm.java.data.entities.utils.Difficulty;
import ru.vsu.amm.java.data.entities.utils.Topic;
import ru.vsu.amm.java.data.repository.DbCardRepository;
import ru.vsu.amm.java.domain.entities.Card;
import ru.vsu.amm.java.domain.entities.WordToAction;

import java.sql.SQLException;
import java.util.List;


public class CardMapper {
    private final DbCardRepository dbCardRepository;

    public CardMapper() {
        this.dbCardRepository = new DbCardRepository();
    }

    public Card toDomain(DbCard dbCard, List<WordToAction> words) {//мб не выносить dbWords
        if (dbCard == null) {
            return null;
        }

        return new Card(
                dbCard.getId(),
                Topic.valueOf(dbCard.getTopic()),
                words,
                Difficulty.valueOf(dbCard.getDifficulty())
        );
    }

    public DbCard toData(Card card) {
        if (card == null){
            return null;
        }

        try {

            return new DbCard(
                    card.getId(),
                    card.getTopic().toString(),
                    card.getDifficulty().toString(),
                    dbCardRepository.findById(card.getId()).getPlayerId()
            );
        } catch (SQLException e) {
            throw new RuntimeException("CardMapper Error", e);
        }

    }
}

