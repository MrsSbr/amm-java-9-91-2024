package ru.vsu.amm.java.domain.mapper;

import main.data.entities.DbCard;
import main.data.entities.DbWordToAction;
import main.data.repository.DbCardRepository;
import main.data.utils.Difficulty;
import main.domain.entities.Card;
import main.domain.entities.WordToAction;

import java.sql.SQLException;
import java.util.List;

public class CardMapper {
    private final WordToActionMapper wordToActionMapper;
    private final DbCardRepository dbCardRepository;

    public CardMapper() {//мб добавить сюда ворд маппер
        this.wordToActionMapper = new WordToActionMapper();
        this.dbCardRepository = new DbCardRepository();
    }

    public Card toDomain(DbCard dbCard, List<DbWordToAction> dbWords) {//мб не выносить dbWords
        if (dbCard == null) {
            return null;
        }

        List<WordToAction> words = dbWords.stream()
                .map(wordToActionMapper::toDomain)
                .toList();

        return new Card(
                dbCard.getId(),
                dbCard.getTopic(),
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
                    card.getTopic(),
                    card.getDifficulty().toString(),
                    dbCardRepository.findById(card.getId()).getPlayerId()
                    );
        } catch (SQLException e) {
            throw new RuntimeException("CardMapper Error", e);
        }

    }
}
