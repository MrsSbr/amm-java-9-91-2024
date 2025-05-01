package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.Column;

import java.util.List;
import java.util.UUID;

public interface ColumnServiceInterface {

    Column createColumn(UUID boardID, String title);
    Column getColumnByID(UUID columnID);
    List<Column> getColumnsByBoard(UUID boardID);
    Column updateColumnTitle(UUID columnID, String newTitle);
    boolean deleteColumn(UUID columnID);

}
