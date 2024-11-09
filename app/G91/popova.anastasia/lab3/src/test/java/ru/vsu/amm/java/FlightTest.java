package ru.vsu.amm.java;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class FlightTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    public void countPeopleByFlightTest() {
        var testRecords = new ArrayList<FamilyRecord>();

        testRecords.add(new FamilyRecord(1, 3));
        testRecords.add(new FamilyRecord(1, 4));
        testRecords.add(new FamilyRecord(2, 5));
        testRecords.add(new FamilyRecord(2, 2));
        testRecords.add(new FamilyRecord(3, 3));

        FlightProcessing.countPeopleByFlight(testRecords);

        assertEquals("flight 1: 7 passengers" + System.lineSeparator() +
                "flight 2: 7 passengers" + System.lineSeparator() +
                "flight 3: 3 passengers",
                outputStream.toString().trim() );
    }

    @Test
    public void countEmptyTest() {
        var testRecords = new ArrayList<FamilyRecord>();

        FlightProcessing.countPeopleByFlight(testRecords);

        assertEquals("", outputStream.toString().trim() );
    }

    @Test
    public void countEmptyFamilyTest() {
        var testRecords = new ArrayList<FamilyRecord>();

        testRecords.add(new FamilyRecord(1, 0));

        var flight = new Flight(testRecords);

        FlightProcessing.countPeopleByFlight(testRecords);

        assertEquals("flight 1: 0 passengers" + System.lineSeparator(),
                outputStream.toString().trim() );
    }

    @Test
    public void getFlightRecordsTest() {
        var testRecords = new ArrayList<FamilyRecord>();

        testRecords.add(new FamilyRecord(1, 3));
        testRecords.add(new FamilyRecord(1, 4));
        testRecords.add(new FamilyRecord(2, 5));
        testRecords.add(new FamilyRecord(2, 2));
        testRecords.add(new FamilyRecord(3, 3));
        testRecords.add(new FamilyRecord(4, 2));
        testRecords.add(new FamilyRecord(4, 2));
        testRecords.add(new FamilyRecord(5, 7));

        var flight = new Flight(testRecords);

        assertIterableEquals(testRecords, flight.getRecords());
    }

    @Test
    public void addFlightRecordsTest() {
        var flight = new Flight(0);

        assertTrue(flight.getRecords().isEmpty());

        var record = new FamilyRecord(1, 5);
        flight.addRecord(record);
        assertFalse(flight.getRecords().isEmpty());
        assertEquals(flight.getRecords().getFirst(), record);
    }

    @Test
    public void removeFromFlightTest(){
        var testRecords = new ArrayList<FamilyRecord>();

        var record = new FamilyRecord(1, 3);
        testRecords.add(record);

        var flight = new Flight(testRecords);
        assertFalse(flight.getRecords().isEmpty());

        assertTrue(flight.removeRecord(record));
        assertTrue(flight.getRecords().isEmpty());

        flight.addRecord(new FamilyRecord(7, 2));
        flight.removeRecordByIndex(0);
        assertTrue(flight.getRecords().isEmpty());

        assertThrows(IndexOutOfBoundsException.class, ()->flight.removeRecordByIndex(25));
    }

    @Test
    public void containsRecordsTest() {
        var testRecords = new ArrayList<FamilyRecord>();

        var record = new FamilyRecord(1, 5);
        testRecords.add(record);

        var flight = new Flight(testRecords);
        assertTrue(flight.containsRecord(record));

        record = new FamilyRecord(7, 7);
        assertFalse(flight.containsRecord(record));
    }


}
