package ru.vsu.amm.java.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entities.Board;
import ru.vsu.amm.java.entities.Column;
import ru.vsu.amm.java.repository.BoardRepository;
import ru.vsu.amm.java.repository.ColumnRepository;
import ru.vsu.amm.java.repository.TaskRepository;
import java.util.List;
import java.util.UUID;

public class BoardService implements BoardServiceInterface {
    private static final Logger log = LoggerFactory.getLogger((BoardService.class));

    private final BoardRepository boardRepository;
    private final ColumnRepository columnRepository;

    /*
    public BoardService (BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
        this.columnRepository = new ColumnRepository();
    }
     */

    public BoardService (BoardRepository boardRepository, ColumnRepository columnRepository) {
        this.boardRepository = boardRepository;
        this.columnRepository = columnRepository;
    }

    public void createDefaultColumns(UUID boardID) {
        String[] defaultTitles = {"Сделать", "В процессе", "Готово"};
        log.debug("Creating default columns for board: {}", boardID);
        for (String title : defaultTitles) {
            try {
                Column column = new Column();
                column.setColumnID(UUID.randomUUID());
                column.setBoardID(boardID);
                column.setColumnTitle(title);
                columnRepository.save(column);
                log.debug("Created default column '{}' for board: {}", title, boardID);
            } catch (RuntimeException e) {
                log.error("Failed to create default column '{}' for board: {}", title, boardID, e);
                throw e;
            }
        }
    }

    @Override
    public Board createBoard(UUID userID, String title, String description) {
        log.debug("Creating board for user: {}, title: {}", userID, title);
        try {
            Board board = new Board();
            board.setBoardID(UUID.randomUUID());
            board.setUserID(userID);
            board.setBoardTitle(title);
            board.setBoardDescription(description);
            boardRepository.save(board);
            createDefaultColumns(board.getBoardID());
            log.info("Created board successfully: {}", board.getBoardID());
            return board;
        } catch (RuntimeException e) {
            log.error("Failed to create board for user: {}, title: {}", userID, title, e);
            throw e;
        }
    }

    @Override
    public Board getBoardByID(UUID boardID) {
        log.debug("Fetching board with ID: {}", boardID);
        try {
            Board board = boardRepository.getByID(boardID);
            log.info("Fetched board successfully: {}", boardID);
            return board;
        } catch (RuntimeException e) {
            log.error("Failed to fetch board with ID: {}", boardID, e);
            throw e;
        }
    }

    @Override
    public List<Board> getAllBoards() {
        log.debug("Fetching all boards");
        try {
            List<Board> boards = boardRepository.getAll();
            log.info("Successfully fetched {} boards", boards.size());
            return boards;
        } catch (RuntimeException e) {
            log.error("Failed to fetch all boards", e);
            throw e;
        }
    }

    @Override
    public Board updateBoardTitle(UUID boardID, String newTitle) {
        log.debug("Updating title for board: {}, newTitle: {}", boardID, newTitle);
        try {
            Board board = boardRepository.getByID(boardID);
            if (board != null) {
                if (newTitle != null) {
                    board.setBoardTitle(newTitle);
                }
                boardRepository.update(board);
                log.info("Updated title for board successfully: {}", boardID);
            }
            return board;
        } catch (RuntimeException e) {
            log.error("Failed to update title for board: {}", boardID, e);
            throw e;
        }
    }

    @Override
    public Board updateBoardDescription(UUID boardID, String newDescription) {
        log.debug("Updating description for board: {}, newDescription: {}", boardID, newDescription);
        try {
            Board board = boardRepository.getByID(boardID);
            if (board != null){
                if (newDescription != null) {
                    board.setBoardDescription(newDescription);
                    log.info("Successfully updated description for board: {}", boardID);
                }
                boardRepository.update(board);
            }
            return board;
        } catch (RuntimeException e) {
            log.error("Failed to update description for board: {}", boardID, e);
            throw e;
        }
    }

    @Override
    public void deleteBoard(UUID boardID) {
        log.debug("Deleting board: {}", boardID);
        try {
            List<Column> columns = columnRepository.getColumnsByBoardId(boardID);
            for (Column column : columns) {
                TaskRepository.deleteTasksByColumnId(column.getColumnID());
                log.debug("Deleted tasks for column: {}", column.getColumnID());
            }
            columnRepository.deleteColumnsByBoardId(boardID);
            log.debug("Deleted columns for board: {}", boardID);
            boardRepository.delete(boardID);
            log.info("Successfully deleted board: {}", boardID);
        } catch (RuntimeException e) {
            log.error("Failed to delete board: {}", boardID, e);
            throw e;
        }
    }

}
