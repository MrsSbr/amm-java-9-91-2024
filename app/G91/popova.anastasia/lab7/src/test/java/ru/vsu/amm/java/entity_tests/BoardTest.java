package ru.vsu.amm.java.entity_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.Board;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {
    private Board board;
    private UUID userID;
    private String boardTitle;
    private String boardDescription;

    @BeforeEach
    void setUp() {
        userID = UUID.randomUUID();
        boardTitle = "TEST BOARD";
        boardDescription = "DESCRIPTION";
        board = new Board();
        board.setUserID(userID);
        board.setBoardTitle(boardTitle);
        board.setBoardDescription(boardDescription);
    }

    @Test
    void testCreateBoard() {
        Board board = new Board();

        assertNotNull(board);
        assertNotNull(board.getBoardID());
        assertNull(board.getUserID());
        assertNull(board.getBoardTitle());
        assertNull(board.getBoardDescription());
    }

    @Test
    void testBoardGettersAndSetters() {
        Board board = new Board();
        UUID boardID = UUID.randomUUID();
        UUID userID = UUID.randomUUID();
        String title = "TEST TASK";
        String description = "DESCRIPTION";

        board.setBoardID(boardID);
        board.setUserID(userID);
        board.setBoardTitle(title);
        board.setBoardDescription(description);

        assertEquals(boardID, board.getBoardID());
        assertEquals(userID, board.getUserID());
        assertEquals(title, board.getBoardTitle());
        assertEquals(description, board.getBoardDescription());
    }

}
