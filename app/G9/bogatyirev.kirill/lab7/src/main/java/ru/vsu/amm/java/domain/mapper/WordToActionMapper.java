package ru.vsu.amm.java.domain.mapper;



import ru.vsu.amm.java.data.entities.DbWordToAction;
import ru.vsu.amm.java.data.repository.DbWordToActionRepository;
import ru.vsu.amm.java.domain.entities.WordToAction;
import ru.vsu.amm.java.domain.repository.ActionRepository;

import java.sql.SQLException;

public class WordToActionMapper {
    private final ActionRepository actionRepository;
    private final DbWordToActionRepository dbWordToActionRepository;

    public WordToActionMapper() {
        this.actionRepository = new ActionRepository();
        this.dbWordToActionRepository = new DbWordToActionRepository();
    }

    public WordToAction toDomain(DbWordToAction dbWordToAction) {
        if (dbWordToAction == null) {
            return null;
        }

        try {
            return new WordToAction(
                    dbWordToAction.getId(),
                    dbWordToAction.getWord(),
                    actionRepository.findById(dbWordToAction.getActionId())
            );
        } catch (SQLException e) {
            throw new RuntimeException("WordToActionMapper Error", e);
        }

    }

    public DbWordToAction toData(WordToAction wordToAction) {
        if (wordToAction == null) {
            return null;
        }

        try {
            return new DbWordToAction(
                    wordToAction.getId(),
                    wordToAction.getWord(),
                    wordToAction.getAction().getId(),
                    dbWordToActionRepository.findById(wordToAction.getId()).getCardId()
            );
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }
    }

}
