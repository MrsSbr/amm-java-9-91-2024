package ru.vsu.amm.java.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entities.Column;
import ru.vsu.amm.java.repository.ColumnRepository;
import java.util.List;
import java.util.UUID;

public class ColumnService implements ColumnServiceInterface {
    private static final Logger log = LoggerFactory.getLogger((ColumnService.class));

    private final ColumnRepository columnRepository;

    public ColumnService(ColumnRepository columnRepository) {
        this.columnRepository = columnRepository;
    }

    @Override
    public Column createColumn(UUID boardID, String title) {
        log.debug("Creating column for board: {}, title: {}", boardID, title);
        try {
            Column column = new Column();
            column.setBoardID(boardID);
            column.setColumnTitle(title);
            columnRepository.save(column);
            log.info("Created column successfully: {}", column.getColumnID());
            return column;
        } catch (RuntimeException e) {
            log.error("Failed to create column for board: {}, title: {}", boardID, title, e);
            throw e;
        }
    }

    @Override
    public Column getColumnByID(UUID columnID) {
        log.debug("Fetching column with ID: {}", columnID);
        try {
            Column column = columnRepository.getByID(columnID);
            log.info("Fetched column successfully: {}", columnID);
            return column;
        } catch(RuntimeException e) {
            log.error("Failed to fetch column with ID: {}", columnID, e);
            throw e;
        }
    }

    @Override
    public List<Column> getColumnsByBoard(UUID boardID) {
        log.debug("Fetching columns for board: {}", boardID);
        try {
            List<Column> columns = columnRepository.getColumnsByBoardId(boardID);
            log.info("Successfully fetched {} columns for board: {}", columns.size(), boardID);
            return columns;
        } catch(RuntimeException e) {
            log.error("Failed to fetch columns for board: {}", boardID, e);
            throw e;
        }
    }

    @Override
    public Column updateColumnTitle(UUID columnID, String newTitle) {
        log.debug("Updating title for column: {}, newTitle: {}", columnID, newTitle);
        try {
            Column column = columnRepository.getByID(columnID);
            if (column != null && newTitle != null) {
                column.setColumnTitle(newTitle);
                columnRepository.update(column);
                log.info("Successfully updated title for column: {}", columnID);
            }
            return column;
        } catch(RuntimeException e) {
            log.error("Failed to update title for column: {}", columnID, e);
            throw e;
        }
    }

    @Override
    public void deleteColumn(UUID columnID) {
        log.debug("Deleting column: {}", columnID);
        try {
            columnRepository.delete(columnID);
            log.info("Deleted column successfully: {}", columnID);
        } catch (RuntimeException e) {
            log.error("Failed to delete column: {}", columnID, e);
            throw e;
        }
    }

}
