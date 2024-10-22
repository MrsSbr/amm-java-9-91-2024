package ru.vsu.amm.java;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AncientWallTest {

    @Test
    public void getRecordsFromAncientWallTest() {
        var testRecords = new ArrayList<AncientRecord>();

        testRecords.add(new AncientRecord(
                LocalDate.of(-39999, 1, 2),
                200,
                HunterName.Booga
        ));
        testRecords.add(new AncientRecord(
                LocalDate.of(-39998, 3, 4),
                230,
                HunterName.Booga
        ));
        testRecords.add(new AncientRecord(
                LocalDate.of(-31004, 5, 6),
                499,
                HunterName.Wooga
        ));
        testRecords.add(new AncientRecord(
                LocalDate.of(-35555, 7, 8),
                175,
                HunterName.Googa
        ));
        testRecords.add(new AncientRecord(
                LocalDate.of(-34343, 9, 10),
                333,
                HunterName.Ooga
        ));

        var wall = new AncientWall(testRecords);

        assertIterableEquals(testRecords, wall.getRecords());
    }

    @Test
    public void addIntoAncientWallTest() {
        var wall = new AncientWall(0);

        assertTrue(wall.getRecords().isEmpty());

        var record = new AncientRecord(
                LocalDate.of(-39999, 1, 2),
                200,
                HunterName.Booga
        );
        wall.addRecord(record);
        assertFalse(wall.getRecords().isEmpty());
        assertEquals(wall.getRecords().getFirst(), record);
    }

    @Test
    public void removeFromAncientWallTest() {
        var testRecords = new ArrayList<AncientRecord>();

        var record = new AncientRecord(
                LocalDate.of(-39999, 1, 2),
                200,
                HunterName.Booga
        );
        testRecords.add(record);

        var wall = new AncientWall(testRecords);
        assertFalse(wall.getRecords().isEmpty());

        assertTrue(wall.removeRecord(record));
        assertTrue(wall.getRecords().isEmpty());

        wall.addRecord(new AncientRecord(
                LocalDate.of(-39998, 3, 4),
                230,
                HunterName.Booga
        ));
        wall.removeRecord(0);
        assertTrue(wall.getRecords().isEmpty());

        assertThrows(IndexOutOfBoundsException.class, () -> wall.removeRecord(10));
    }

    @Test
    public void containsInAncientWallTest() {
        var testRecords = new ArrayList<AncientRecord>();

        var record = new AncientRecord(
                LocalDate.of(-39999, 1, 2),
                200,
                HunterName.Booga
        );
        testRecords.add(record);

        var wall = new AncientWall(testRecords);
        assertTrue(wall.containsRecord(record));

        record = new AncientRecord(
                LocalDate.of(-39998, 3, 4),
                230,
                HunterName.Booga
        );
        assertFalse(wall.containsRecord(record));
    }
}
