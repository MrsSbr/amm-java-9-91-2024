package ru.vsu.amm.java.service_tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vsu.amm.java.entities.Board;
import ru.vsu.amm.java.entities.Column;
import ru.vsu.amm.java.repository.BoardRepository;
import ru.vsu.amm.java.repository.ColumnRepository;
import ru.vsu.amm.java.repository.TaskRepository;
import ru.vsu.amm.java.service.BoardService;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {
    @Mock
    private BoardRepository boardRepository;

    @Mock
    private ColumnRepository columnRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private BoardService boardService;

    private UUID userID;
    private UUID boardID;
    private Board board;
    private MockedStatic<TaskRepository> mockedTaskRepository;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        userID = UUID.randomUUID();
        boardID = UUID.randomUUID();
        board = new Board();
        board.setBoardID(boardID);
        board.setUserID(userID);
        board.setBoardTitle("TEST BOARD");
        board.setBoardDescription("DESCRIPTION");
        boardService = new BoardService(boardRepository, columnRepository);

        Field columnRepositoryField = BoardService.class.getDeclaredField("columnRepository");
        mockedTaskRepository = mockStatic(TaskRepository.class);
    }

    @AfterEach
    void tearDown() {
        mockedTaskRepository.close();
    }

    @Test
    void testCreateBoard() {
       doAnswer(invokation -> {
            Board b = invokation.getArgument(0);
            b.setBoardID(boardID);
            return null;
        }).when(boardRepository).save(any(Board.class));

        BoardService boardServiceSpy = spy(boardService);
        doNothing().when(boardServiceSpy).createDefaultColumns(any(UUID.class));

        Board createdBoard = boardServiceSpy.createBoard(userID, "NEW BOARD", "DESCRIPTION");

        assertNotNull(createdBoard);
        assertEquals(userID, createdBoard.getUserID());
        assertEquals("NEW BOARD", createdBoard.getBoardTitle());
        assertEquals("DESCRIPTION", createdBoard.getBoardDescription());

        verify(boardRepository).save(any(Board.class));
        verify(boardServiceSpy).createDefaultColumns(boardID);
    }

    @Test
    void testGetBoardByID() {
        when(boardRepository.getByID(boardID)).thenReturn(board);

        Board result = boardService.getBoardByID(boardID);

        assertNotNull(result);
        assertEquals(boardID, result.getBoardID());
        verify(boardRepository).getByID(boardID);
    }

    @Test
    void testGetAllBoards() {
        when(boardRepository.getAll()).thenReturn(List.of(board));

        List<Board> result = boardService.getAllBoards();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(boardID, result.get(0).getBoardID());
        verify(boardRepository).getAll();
    }

    @Test
    void testUpdateBoardTitle() {
        String newTitle = "UPDATED TITLE";
        when(boardRepository.getByID(boardID)).thenReturn(board);
        doNothing().when(boardRepository).update(any(Board.class));

        Board result = boardService.updateBoardTitle(boardID, newTitle);

        assertNotNull(result);
        assertEquals(newTitle, result.getBoardTitle());
        verify(boardRepository).getByID(boardID);
        verify(boardRepository).update(any(Board.class));
    }

    @Test
    void testUpdateBoardDescription() {
        String newDescription = "UPDATED DESCRIPTION";
        when(boardRepository.getByID(boardID)).thenReturn(board);
        doNothing().when(boardRepository).update(any(Board.class));

        Board result = boardService.updateBoardDescription(boardID, newDescription);

        assertNotNull(result);
        assertEquals(newDescription, result.getBoardDescription());
        verify(boardRepository).getByID(boardID);
        verify(boardRepository).update(any(Board.class));
    }

    @Test
    void testDeleteBoard() {
        UUID columnID = UUID.randomUUID();
        Column column = new Column();
        column.setColumnID(columnID);
        column.setBoardID(boardID);
        column.setColumnTitle("TEST COLUMN");
        List<Column> columns = Arrays.asList(column);

        when(columnRepository.getColumnsByBoardId(boardID)).thenReturn(columns);
        mockedTaskRepository.when(() -> TaskRepository.deleteTasksByColumnId(columnID)).thenAnswer(invocation -> null);
        doNothing().when(columnRepository).deleteColumnsByBoardId(boardID);
        doNothing().when(boardRepository).delete(boardID);

        boardService.deleteBoard(boardID);

        verify(columnRepository).getColumnsByBoardId(boardID);
        mockedTaskRepository.verify(() -> TaskRepository.deleteTasksByColumnId(columnID));
        verify(columnRepository).deleteColumnsByBoardId(boardID);
        verify(boardRepository).delete(boardID);
    }

}
