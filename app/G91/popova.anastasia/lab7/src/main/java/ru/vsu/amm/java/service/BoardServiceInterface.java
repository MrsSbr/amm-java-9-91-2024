package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.Board;

import java.util.List;
import java.util.UUID;

public interface BoardServiceInterface {
    Board createBoard(UUID userID, String title, String description);
    Board getBoardByID(UUID boardID);
    List<Board> getAllBoards();
    Board updateBoardTitle(UUID boardID, String newTitle);
    Board updateBoardDescription(UUID boardID, String newDescription);
    void deleteBoard(UUID boardID);
}
