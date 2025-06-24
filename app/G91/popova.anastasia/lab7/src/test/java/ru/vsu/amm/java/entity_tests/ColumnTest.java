package ru.vsu.amm.java.entity_tests;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.Column;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ColumnTest {
    @Test
    public void testCreateColumn() {
        Column column = new Column();
        assertNotNull(column.getColumnID());
        assertNull(column.getBoardID());
        assertNull(column.getColumnTitle());
    }

    @Test
    public void testColumnSettersAndGetters() {
        Column column = new Column();

        UUID testBoardID = UUID.randomUUID();
        String testTitle = "TEST COLUMN";

        column.setBoardID(testBoardID);
        column.setColumnTitle(testTitle);

        assertEquals(testBoardID, column.getBoardID());
        assertEquals(testTitle, column.getColumnTitle());
    }

}
