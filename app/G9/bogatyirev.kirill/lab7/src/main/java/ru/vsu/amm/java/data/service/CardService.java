package ru.vsu.amm.java.data.service;


import ru.vsu.amm.java.data.entities.DbCard;
import ru.vsu.amm.java.data.entities.utils.Difficulty;
import ru.vsu.amm.java.data.entities.utils.Topic;
import ru.vsu.amm.java.data.repository.DbCardRepository;
import ru.vsu.amm.java.domain.entities.Card;
import ru.vsu.amm.java.domain.entities.WordToAction;
import ru.vsu.amm.java.domain.mapper.CardMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class CardService {
    private final DbCardRepository dbCardRepository;
    private final WordToActionService wordToActionService;
    private final CardMapper cardMapper;

    private final int COUNT_WORD_TO_ACTION = 6;
    private static final Logger log = Logger.getLogger(CardService.class.getName());

    public CardService() {
        dbCardRepository = new DbCardRepository();
        wordToActionService = new WordToActionService();
        cardMapper = new CardMapper();
    }

    public Card generateCard(Long playerId) {
        DbCard dbCard = new DbCard(Topic.randomTopic().name(), Difficulty.randomDifficulty().name(), playerId);

        try {
            dbCardRepository.create(dbCard);
            List<WordToAction> wordToActions = wordToActionService.generateWordToActions(COUNT_WORD_TO_ACTION, dbCard.getId());

            log.info("Карточка сгенерировалась" + dbCard.getId() + " " + dbCard.getTopic());
            return cardMapper.toDomain(dbCard, wordToActions);
        } catch (SQLException e) {
            throw new RuntimeException("GenerateCard exception", e);
        }

    }

    public int rollDice() {
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }
}
