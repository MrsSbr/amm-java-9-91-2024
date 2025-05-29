package ru.vsu.amm.java.domain.repository;



import ru.vsu.amm.java.data.entities.DbWordToAction;
import ru.vsu.amm.java.data.repository.DbWordToActionRepository;
import ru.vsu.amm.java.domain.entities.WordToAction;
import ru.vsu.amm.java.domain.mapper.WordToActionMapper;

import java.sql.SQLException;
import java.util.List;

public class WordToActionRepository implements Repository<WordToAction> {
    private final DbWordToActionRepository dbWordToActionRepository;
    private final WordToActionMapper wordToActionMapper;

    public WordToActionRepository() {
        this.dbWordToActionRepository = new DbWordToActionRepository();
        this.wordToActionMapper = new WordToActionMapper();
    }

    @Override
    public WordToAction findById(Long id) throws SQLException {

        try {
            DbWordToAction dbWordToAction = dbWordToActionRepository.findById(id);
            return wordToActionMapper.toDomain(dbWordToAction);
        } catch (SQLException e) {
            throw new RuntimeException("WordToActionRepository find exception", e);
        }

    }

    @Override
    public void create(WordToAction wordToAction) throws SQLException {

        try {
            DbWordToAction dbWordToAction = wordToActionMapper.toData(wordToAction);
            dbWordToActionRepository.create(dbWordToAction);
        } catch (SQLException e) {
            throw new RuntimeException("WordToActionRepository create exception", e);
        }

    }

    @Override
    public void update(WordToAction wordToAction) throws SQLException {

        try {
            DbWordToAction dbWordToAction = wordToActionMapper.toData(wordToAction);
            dbWordToActionRepository.update(dbWordToAction);
        } catch (SQLException e) {
            throw new RuntimeException("WordToActionRepository update exception", e);
        }

    }

    @Override
    public void delete(WordToAction wordToAction) throws SQLException {

        try {
            DbWordToAction dbWordToAction = wordToActionMapper.toData(wordToAction);
            dbWordToActionRepository.delete(dbWordToAction);
        } catch (SQLException e) {
            throw new RuntimeException("WordToActionRepository delete exception", e);
        }

    }

    public List<WordToAction> findByCardId(Long id) {

        try {
            return dbWordToActionRepository.findByCardId(id)
                    .stream()
                    .map(wordToActionMapper::toDomain)
                    .toList();
        } catch (SQLException e) {
            throw new RuntimeException("WordToActionRepository find by card id issue", e);
        }
    }
}
