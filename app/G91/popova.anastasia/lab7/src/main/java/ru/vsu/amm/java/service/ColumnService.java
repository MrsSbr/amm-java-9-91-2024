package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.Column;
import ru.vsu.amm.java.repository.ColumnRepository;

import java.util.List;
import java.util.UUID;


public class ColumnService implements ColumnServiceInterface {
    private final ColumnRepository columnRepository;

    public ColumnService(ColumnRepository columnRepository) {
        this.columnRepository = columnRepository;
    }

    @Override
    public Column createColumn(UUID boardID, String title) {
        Column column = new Column();
        column.setBoardID(boardID);
        column.setColumnTitle(title);
        columnRepository.save(column);
        return column;
    }

    @Override
    public Column getColumnByID(UUID columnID) {
        return columnRepository.getByID(columnID);
    }

    @Override
    public List<Column> getColumnsByBoard(UUID bosrdID) {
        List<Column> columns = columnRepository.getAll();
        return columns.stream()
                .filter(column -> column.getBoardID().equals(bosrdID))
                .toList();
    }

    @Override
    public Column updateColumnTitle(UUID columnID, String newTitle) {
        Column column = columnRepository.getByID(columnID);
        if (column != null && newTitle != null) {
            column.setColumnTitle(newTitle);
            columnRepository.update(column);
        }
        return column;
    }

    @Override
    public void deleteColumn(UUID columnID) {
        Column column = columnRepository.getByID(columnID);
        columnRepository.delete(columnID);
    }

}
