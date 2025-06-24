package ru.vsu.amm.java.repository_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vsu.amm.java.DB_config.DatabaseConnection;
import ru.vsu.amm.java.entities.Column;
import ru.vsu.amm.java.repository.ColumnRepository;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ColumnRepositoryTest {
    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement ps;

    @Mock
    private ResultSet rs;

    @InjectMocks
    private ColumnRepository columnRepository;

    private MockedStatic<DatabaseConnection> mockedDatabaseConnection;
    private UUID columnID;
    private UUID boardID;
    private Column column;

    @BeforeEach
    void setUp() {
        columnID = UUID.randomUUID();
        boardID = UUID.randomUUID();
        column = new Column();
        column.setColumnID(columnID);
        column.setBoardID(boardID);
        column.setColumnTitle("TEST COLUMN");

        mockedDatabaseConnection = mockStatic(DatabaseConnection.class);
        mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(connection);
    }

    @AfterEach
    void tearDown() {
        mockedDatabaseConnection.close();
    }

    @Test
    void testGetColumnByID() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getObject("ColumnID")).thenReturn(columnID);
        when(rs.getObject("BoardID")).thenReturn(boardID);
        when(rs.getString("ColumnTitle")).thenReturn("TEST COLUMN");

        Column result = columnRepository.getByID(columnID);

        assertNotNull(result);
        assertEquals(columnID, result.getColumnID());
        assertEquals(boardID, result.getBoardID());
        assertEquals("TEST COLUMN", result.getColumnTitle());
        verify(ps).setObject(1, columnID);
        verify(ps).executeQuery();
    }

    @Test
    void testGetAll() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false); // One column
        when(rs.getObject("ColumnID")).thenReturn(columnID);
        when(rs.getObject("BoardID")).thenReturn(boardID);
        when(rs.getString("ColumnTitle")).thenReturn("TEST COLUMN");

        List<Column> result = columnRepository.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        Column resultColumn = result.get(0);
        assertEquals(columnID, resultColumn.getColumnID());
        assertEquals(boardID, resultColumn.getBoardID());
        assertEquals("TEST COLUMN", resultColumn.getColumnTitle());
        verify(ps).executeQuery();
    }

    @Test
    void testSaveSuccess() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        columnRepository.save(column);

        verify(ps).setObject(1, columnID);
        verify(ps).setObject(2, boardID);
        verify(ps).setObject(3, "TEST COLUMN");
        verify(ps).executeUpdate();
    }

    @Test
    void testUpdateSuccess() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        columnRepository.update(column);

        verify(ps).setString(1, "TEST COLUMN");
        verify(ps).setObject(2, columnID);
        verify(ps).executeUpdate();
    }

    @Test
    void testDeleteSuccess() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        columnRepository.delete(columnID);

        verify(ps).setObject(1, columnID);
        verify(ps).executeUpdate();
    }

    @Test
    void testGetColumnsByBoardIdSuccess() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getObject("ColumnID")).thenReturn(columnID);
        when(rs.getObject("BoardID")).thenReturn(boardID);
        when(rs.getString("ColumnTitle")).thenReturn("Test Column");

        List<Column> result = columnRepository.getColumnsByBoardId(boardID);

        assertNotNull(result);
        assertEquals(1, result.size());
        Column resultColumn = result.get(0);
        assertEquals(columnID, resultColumn.getColumnID());
        assertEquals(boardID, resultColumn.getBoardID());
        assertEquals("Test Column", resultColumn.getColumnTitle());
        verify(ps).setObject(1, boardID);
        verify(ps).executeQuery();
    }

    @Test
    void testDeleteColumnsByBoardIdSuccess() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(2);

        columnRepository.deleteColumnsByBoardId(boardID);

        verify(ps).setObject(1, boardID);
        verify(ps).executeUpdate();
    }

}
