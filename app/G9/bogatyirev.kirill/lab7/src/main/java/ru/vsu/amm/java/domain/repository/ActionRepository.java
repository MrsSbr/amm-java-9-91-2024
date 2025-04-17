package ru.vsu.amm.java.domain.repository;

import main.data.entities.DbAction;
import main.data.repository.DbActionRepository;
import main.domain.entities.Action;
import main.domain.mapper.ActionMapper;

import java.sql.SQLException;

public class ActionRepository implements Repository<Action> {
    private final DbActionRepository dbActionRepository;
    private final ActionMapper actionMapper;

    public ActionRepository() {
        this.dbActionRepository = new DbActionRepository();
        this.actionMapper = new ActionMapper();
    }


    @Override
    public Action findById(Long id) throws SQLException {

        try {
            DbAction dbAction = dbActionRepository.findById(id);
            return actionMapper.toDomain(dbAction);

        } catch (SQLException e) {
            throw new RuntimeException("Cannot find action by id", e);
        }

    }

    @Override
    public void create(Action action) throws SQLException {

        try {
            DbAction dbAction = actionMapper.toData(action);
            dbActionRepository.create(dbAction);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot create action", e);
        }

    }

    @Override
    public void update(Action action) throws SQLException {

        try {
            DbAction dbAction = actionMapper.toData(action);
            dbActionRepository.update(dbAction);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot update action", e);
        }

    }

    @Override
    public void delete(Action action) throws SQLException {

        try {
            DbAction dbAction = actionMapper.toData(action);
            dbActionRepository.update(dbAction);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot delete action", e);
        }

    }
}
