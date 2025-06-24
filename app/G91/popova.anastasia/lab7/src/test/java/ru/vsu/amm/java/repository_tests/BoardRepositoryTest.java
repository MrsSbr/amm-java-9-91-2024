package ru.vsu.amm.java.repository_tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vsu.amm.java.DB_config.DatabaseConnection;
import ru.vsu.amm.java.entities.Board;
import ru.vsu.amm.java.repository.BoardRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BoardRepositoryTest {
    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement ps;

    @Mock
    private ResultSet rs;

    private BoardRepository boardRepository;
    private UUID boardID;
    private UUID userID;
    private Board board;
    private MockedStatic<DatabaseConnection> mockedDBConnection;

    @BeforeEach
    void setUp() throws SQLException {
        boardID = UUID.randomUUID();
        userID = UUID.randomUUID();
        board = new Board();
        board.setBoardID(boardID);
        board.setUserID(userID);
        board.setBoardTitle("TEST BOARD");
        board.setBoardDescription("DESCRIPTION");
        mockedDBConnection = mockStatic(DatabaseConnection.class);
        mockedDBConnection.when(DatabaseConnection::getConnection).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        boardRepository = new BoardRepository();
    }

    @AfterEach
    void tearDown() {
        mockedDBConnection.close();
    }

    @Test
    void testGetByID() throws SQLException {
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getObject("BoardID")).thenReturn(boardID);
        when(rs.getObject("UserID")).thenReturn(userID);
        when(rs.getString("BoardTitle")).thenReturn("Test Board");
        when(rs.getString("BoardDescription")).thenReturn("Board Description");

        Board result = boardRepository.getByID(boardID);

        assertNotNull(result);
        assertEquals(boardID, result.getBoardID());
        assertEquals(userID, result.getUserID());
        assertEquals("Test Board", result.getBoardTitle());
        assertEquals("Board Description", result.getBoardDescription());

        verify(ps).setObject(1, boardID);
        verify(ps).executeQuery();
    }

    @Test
    void testGetAll() throws SQLException {
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getObject("BoardID")).thenReturn(boardID, UUID.randomUUID());
        when(rs.getObject("UserID")).thenReturn(userID, UUID.randomUUID());
        when(rs.getString("BoardTitle")).thenReturn("Board 1", "Board 2");
        when(rs.getString("BoardDescription")).thenReturn("Desc 1", "Desc 2");

        List<Board> result = boardRepository.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Board 1", result.get(0).getBoardTitle());
        assertEquals("Board 2", result.get(1).getBoardTitle());
        verify(ps).executeQuery();
    }

    @Test
    void testSave() throws SQLException {
        when(ps.executeUpdate()).thenReturn(1);

        boardRepository.save(board);

        verify(ps).setObject(1, board.getBoardID());
        verify(ps).setObject(2, board.getUserID());
        verify(ps).setString(3, board.getBoardTitle());
        verify(ps).setString(4, board.getBoardDescription());
        verify(ps).executeUpdate();
    }

    @Test
    void testUpdate() throws SQLException {
        when(ps.executeUpdate()).thenReturn(1);

        boardRepository.update(board);

        verify(ps).setString(1, board.getBoardTitle());
        verify(ps).setString(2, board.getBoardDescription());
        verify(ps).setObject(3, board.getBoardID());
        verify(ps).executeUpdate();
    }

    @Test
    void testUpdateWithNoDescription() throws SQLException {
        board.setBoardDescription(null);
        when(ps.executeUpdate()).thenReturn(1);

        boardRepository.update(board);

        verify(ps).setString(1, board.getBoardTitle());
        verify(ps).setNull(2, java.sql.Types.VARCHAR);
        verify(ps).setObject(3, board.getBoardID());
        verify(ps).executeUpdate();
    }

    @Test
    void testDelete() throws SQLException {
        when(ps.executeUpdate()).thenReturn(1);

        boardRepository.delete(boardID);

        verify(ps).setObject(1, boardID);
        verify(ps).executeUpdate();
    }

    @Test
    void testGetAllEmpty() throws SQLException {
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        List<Board> result = boardRepository.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(ps).executeQuery();
    }

}
