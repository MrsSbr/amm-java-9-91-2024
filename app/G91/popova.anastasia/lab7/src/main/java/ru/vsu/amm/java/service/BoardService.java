package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.Board;
import ru.vsu.amm.java.entities.Column;
import ru.vsu.amm.java.repository.BoardRepository;
import ru.vsu.amm.java.repository.ColumnRepository;
import ru.vsu.amm.java.repository.TaskRepository;

import java.util.List;
import java.util.UUID;

public class BoardService implements BoardServiceInterface {

    private final BoardRepository boardRepository;
    private final ColumnRepository columnRepository;
    private final TaskRepository taskRepository;

    public BoardService (BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
        this.columnRepository = new ColumnRepository();
        this.taskRepository = new TaskRepository();
    }

    private void createDefaultColumns(UUID boardID) {
        String[] defaultTitles = {"Сделать", "В процессе", "Готово"};

        for (String title : defaultTitles) {
            Column column = new Column();
            column.setColumnID(UUID.randomUUID());
            column.setBoardID(boardID);
            column.setColumnTitle(title);

            columnRepository.save(column);
        }
    }

    @Override
    public Board createBoard(UUID userID, String title, String description) {
        Board board = new Board();
        board.setBoardID(UUID.randomUUID());
        board.setUserID(userID);
        board.setBoardTitle(title);
        board.setBoardDescription(description);

        boardRepository.save(board);
        createDefaultColumns(board.getBoardID());
        return board;
    }

    @Override
    public Board getBoardByID(UUID boardID) {
        return boardRepository.getByID(boardID);
    }

    @Override
    public List<Board> getAllBoards() {
        return boardRepository.getAll();
    }

    @Override
    public Board updateBoardTitle(UUID boardID, String newTitle) {
        Board board = boardRepository.getByID(boardID);

        if (board != null) {
            if (newTitle != null) {
                board.setBoardTitle(newTitle);
            }
            boardRepository.update(board);
        }

        return board;
    }

    @Override
    public Board updateBoardDescription(UUID boardID, String newDescription) {
        Board board = boardRepository.getByID(boardID);

        if (board != null){
            if (newDescription != null) {
                board.setBoardDescription(newDescription);
            }
            boardRepository.update(board);
        }

        return board;
    }

    @Override
    public void deleteBoard(UUID boardID) {
        List<Column> columns = columnRepository.getColumnsByBoardId(boardID); // Используем экземпляр
        for (Column column : columns) {
            taskRepository.deleteTasksByColumnId(column.getColumnID());
        }
        columnRepository.deleteColumnsByBoardId(boardID); // Используем экземпляр
        boardRepository.delete(boardID);
    }

}
