package ru.vsu.amm.java.domain.mapper;


import ru.vsu.amm.java.data.entities.DbAction;
import ru.vsu.amm.java.domain.entities.Action;

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
