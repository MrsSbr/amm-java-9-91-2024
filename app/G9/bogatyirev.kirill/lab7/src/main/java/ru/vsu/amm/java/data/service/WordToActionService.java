package ru.vsu.amm.java.data.service;



import ru.vsu.amm.java.data.entities.DbWordToAction;
import ru.vsu.amm.java.data.repository.DbWordToActionRepository;
import ru.vsu.amm.java.domain.entities.Action;
import ru.vsu.amm.java.domain.entities.WordToAction;
import ru.vsu.amm.java.domain.mapper.WordToActionMapper;
import ru.vsu.amm.java.domain.repository.ActionRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class WordToActionService {
    private final ActionRepository actionRepository;
    private final DbWordToActionRepository dbWordToActionRepository;
    private final WordToActionMapper wordToActionMapper;
    private final WordsService wordsService;

    private static final Logger log = Logger.getLogger(WordToActionService.class.getName());

    public WordToActionService() {
        actionRepository = new ActionRepository();
        dbWordToActionRepository = new DbWordToActionRepository();
        wordToActionMapper = new WordToActionMapper();
        wordsService = new WordsService();
    }

    public List<WordToAction> generateWordToActions(int count, Long card_id) throws SQLException {
        List<DbWordToAction> dbWordToActions = new ArrayList<DbWordToAction>();

        List<Action> actions = actionRepository.getAllActions();
        log.info("Список действий размер " + actions.size());
        List<String> words = wordsService.getRandomWords(count);
        for (int i = 0; i < count; i++) {
            DbWordToAction dbWordToAction = new DbWordToAction(
                    words.get(i),
                    actions.get(i).getId(),
                    card_id
            );
            log.info("Сгенерировался WordToAction: " + dbWordToAction.getWord()
             + " " + dbWordToAction.getActionId() + " " + dbWordToAction.getCardId());

            dbWordToActionRepository.create(dbWordToAction);
            log.info("Добавился WordToAction: " + dbWordToAction);
            dbWordToActions.add(dbWordToAction);
        }

        return dbWordToActions.stream()
                .map(wordToActionMapper::toDomain)
                .toList();
    }
}
