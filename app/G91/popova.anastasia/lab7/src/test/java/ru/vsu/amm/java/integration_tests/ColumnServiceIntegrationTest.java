package ru.vsu.amm.java.integration_tests;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.Column;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.BoardRepository;
import ru.vsu.amm.java.repository.ColumnRepository;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.service.BoardService;
import ru.vsu.amm.java.service.ColumnService;
import ru.vsu.amm.java.service.UserService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ColumnServiceIntegrationTest extends BaseIntegrationTest {

    private ColumnService createColumnService() {
        return new ColumnService(new ColumnRepository());
    }

    private UUID createTestBoard() {
        UserService userService = new UserService(new UserRepository());
        User user = userService.register("coluser", "coluser@example.com", "password123");

        BoardService boardService = new BoardService(new BoardRepository(), new ColumnRepository());
        return boardService.createBoard(user.getUserID(), "Test Board", "Description").getBoardID();
    }

    @Test
    void createColumnTest() {
        ColumnService service = createColumnService();
        UUID boardID = createTestBoard();

        Column column = service.createColumn(boardID, "New Column");

        assertNotNull(column.getColumnID());
        assertEquals("New Column", column.getColumnTitle());
    }

    @Test
    void getColumnByIDTest() {
        ColumnService service = createColumnService();
        UUID boardID = createTestBoard();
        Column created = service.createColumn(boardID, "Test Column");

        Column found = service.getColumnByID(created.getColumnID());

        assertEquals(created.getColumnID(), found.getColumnID());
    }

    @Test
    void getColumnsByBoardTest() {
        ColumnService service = createColumnService();
        UUID boardID = createTestBoard();
        service.createColumn(boardID, "Column 1");
        service.createColumn(boardID, "Column 2");

        List<Column> columns = service.getColumnsByBoard(boardID);

        assertTrue(columns.size() >= 2);
    }

    @Test
    void updateColumnTitleTest() {
        ColumnService service = createColumnService();
        UUID boardID = createTestBoard();
        Column column = service.createColumn(boardID, "Old Title");

        Column updated = service.updateColumnTitle(column.getColumnID(), "New Title");

        assertEquals("New Title", updated.getColumnTitle());
    }

    @Test
    void deleteColumnTest() {
        ColumnService service = createColumnService();
        UUID boardID = createTestBoard();
        Column column = service.createColumn(boardID, "To Delete");

        service.deleteColumn(column.getColumnID());

        assertNull(service.getColumnByID(column.getColumnID()));
    }
}