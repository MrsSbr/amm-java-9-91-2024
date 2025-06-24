package ru.vsu.amm.java.service_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vsu.amm.java.entities.Column;
import ru.vsu.amm.java.repository.ColumnRepository;
import ru.vsu.amm.java.service.ColumnService;

import java.util.Arrays;
import java.util.UUID;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ColumnServiceTest {
    @Mock
    private ColumnRepository columnRepository;

    @InjectMocks
    private ColumnService columnService;

    private UUID boardID = UUID.randomUUID();
    private UUID columnID = UUID.randomUUID();
    private Column column;

    @BeforeEach
    void setUp() {
        column = new Column();
        column.setColumnID(columnID);
        column.setBoardID(boardID);
        column.setColumnTitle("TEST COLUMN");
    }

    @Test
    void testCreateColumn() {
        doAnswer(invocation -> {
            Column c = invocation.getArgument(0);
            c.setColumnID(columnID);
            return c;
        }).when(columnRepository).save(any(Column.class));

        Column result = columnService.createColumn(boardID, "NEW COLUMN");

        assertNotNull(result);
        assertEquals(boardID, result.getBoardID());
        assertEquals("NEW COLUMN", result.getColumnTitle());
        assertNotNull(result.getColumnID());
        verify(columnRepository).save(any(Column.class));
    }

    @Test
    void testGetColumnByID() {
        when(columnRepository.getByID(columnID)).thenReturn(column);

        Column result = columnService.getColumnByID(columnID);

        assertNotNull(result);
        assertEquals(columnID, result.getColumnID());
        assertEquals(boardID, result.getBoardID());
        verify(columnRepository).getByID(columnID);
    }

    @Test
    void testGetColumnsByBoard() {
        List<Column> columns = Arrays.asList(column);
        when(columnRepository.getColumnsByBoardId(boardID)).thenReturn(columns);

        List<Column> result = columnService.getColumnsByBoard(boardID);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(columnID, result.get(0).getColumnID());
        verify(columnRepository).getColumnsByBoardId(boardID);
    }

    @Test
    void testUpdateColumnTitle() {
        String newTitle = "UPDATED TITLE";
        when(columnRepository.getByID(columnID)).thenReturn(column);
        doNothing().when(columnRepository).update(any(Column.class));

        Column result = columnService.updateColumnTitle(columnID, newTitle);

        assertNotNull(result);
        assertEquals(newTitle, result.getColumnTitle());
        verify(columnRepository).getByID(columnID);
        verify(columnRepository).update(column);
    }

    @Test
    void testUpdateColumnTitleWithNull() {
        when(columnRepository.getByID(columnID)).thenReturn(column);

        Column result = columnService.updateColumnTitle(columnID, null);

        assertNotNull(result);
        assertEquals("TEST COLUMN", result.getColumnTitle()); // Title should not change
        verify(columnRepository).getByID(columnID);
        verify(columnRepository, never()).update(any());
    }

    @Test
    void testDeleteColumn() {
        doNothing().when(columnRepository).delete(columnID);

        columnService.deleteColumn(columnID);
        verify(columnRepository).delete(columnID);
    }


}
