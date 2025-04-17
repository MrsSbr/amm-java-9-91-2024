package ru.vsu.amm.java.domain.mapper;

import main.data.entities.DbAction;
import main.domain.entities.Action;

public class ActionMapper {
    public Action toDomain(DbAction dbAction) {
        if(dbAction == null) {
            return null;
        }

        return new Action(
                dbAction.getId(),
                dbAction.getName(),
                dbAction.getPoints()
        );
    }

    public DbAction toData(Action action) {
        if(action == null) {
            return null;
        }

        return new DbAction(
                action.getId(),
                action.getName(),
                action.getPoints()
        );
    }
}
