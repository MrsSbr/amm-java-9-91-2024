package ru.vsu.amm.java.domain.repository;

import main.data.entities.DbCard;
import main.data.repository.DbCardRepository;
import main.domain.entities.Card;
import main.domain.mapper.CardMapper;

import java.sql.SQLException;

public class CardRepository implements Repository<Card>{
    private final DbCardRepository dbCardRepository;
    private final CardMapper cardMapper;
    private final WordToActionRepository wordToActionRepository;

    public CardRepository() {
        this.dbCardRepository = new DbCardRepository();
        this.cardMapper = new CardMapper();
        this.wordToActionRepository = new WordToActionRepository();
    }

    @Override
    public Card findById(Long id) throws SQLException {

        try {
            DbCard dbCard = dbCardRepository.findById(id);
            return cardMapper.toDomain(dbCard, wordToActionRepository.findByCardId(id));
        } catch (SQLException e) {
            throw new RuntimeException("CardRepository find exception", e);
        }

    }

    @Override
    public void create(Card card) throws SQLException {

        try {
            DbCard dbCard = cardMapper.toData(card);
            dbCardRepository.create(dbCard);
        } catch (SQLException e) {
            throw new RuntimeException("CardRepository create exception", e);
        }

    }


    @Override
    public void update(Card card) throws SQLException {

        try {
            DbCard dbCard = cardMapper.toData(card);
            dbCardRepository.update(dbCard);
        } catch (SQLException e) {
            throw new RuntimeException("CardRepository update exception", e);
        }

    }

    @Override
    public void delete(Card card) throws SQLException {

        try {
            DbCard dbCard = cardMapper.toData(card);
            dbCardRepository.delete(dbCard);
        } catch (SQLException e) {
            throw new RuntimeException("CardRepository delete exception", e);
        }

    }
}
