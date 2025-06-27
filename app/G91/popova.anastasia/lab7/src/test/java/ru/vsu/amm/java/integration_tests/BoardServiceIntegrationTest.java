package ru.vsu.amm.java.integration_tests;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.Board;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.BoardRepository;
import ru.vsu.amm.java.repository.ColumnRepository;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.service.BoardService;
import ru.vsu.amm.java.service.UserService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BoardServiceIntegrationTest extends BaseIntegrationTest {

    private BoardService createBoardService() {
        return new BoardService(new BoardRepository(), new ColumnRepository());
    }

    private UUID createTestUser() {
        UserService userService = new UserService(new UserRepository());
        User user = userService.register("boarduser", "boarduser@example.com", "password123");
        return user.getUserID();
    }

    @Test
    void createBoardTest() {
        BoardService service = createBoardService();
        UUID userID = createTestUser();

        Board board = service.createBoard(userID, "Test Board", "Description");

        assertNotNull(board.getBoardID());
        assertEquals("Test Board", board.getBoardTitle());
    }

    @Test
    void getBoardByIDTest() {
        BoardService service = createBoardService();
        UUID userID = createTestUser();
        Board created = service.createBoard(userID, "Test Board", "Description");

        Board found = service.getBoardByID(created.getBoardID());

        assertEquals(created.getBoardID(), found.getBoardID());
    }

    @Test
    void getAllBoardsTest() {
        BoardService service = createBoardService();
        UUID userID = createTestUser();
        service.createBoard(userID, "Board 1", "Desc 1");
        service.createBoard(userID, "Board 2", "Desc 2");

        List<Board> boards = service.getAllBoards();

        assertTrue(boards.size() >= 2);
    }

    @Test
    void updateBoardTitleTest() {
        BoardService service = createBoardService();
        UUID userID = createTestUser();
        Board board = service.createBoard(userID, "Old Title", "Description");

        Board updated = service.updateBoardTitle(board.getBoardID(), "New Title");

        assertEquals("New Title", updated.getBoardTitle());
    }

    @Test
    void deleteBoardTest() {
        BoardService service = createBoardService();
        UUID userID = createTestUser();
        Board board = service.createBoard(userID, "To Delete", "Description");

        service.deleteBoard(board.getBoardID());

        assertNull(service.getBoardByID(board.getBoardID()));
    }

}