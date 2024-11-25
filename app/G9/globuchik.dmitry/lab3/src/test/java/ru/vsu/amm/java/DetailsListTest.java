package ru.vsu.amm.java;

//import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static ru.vsu.amm.java.DetailTypes.*;

class DetailsListTest {

    private DetailsList detailsList;

    @BeforeEach
    void setup() {

        detailsList = new DetailsList(0);
        Random rand = new Random();
        int count = rand.nextInt(1000) + 1;

        for (int i = 0; i < count; ++i) {
            detailsList.addPart(DetailTypes.getRandomType());
        }

    }

    @org.junit.jupiter.api.Test
    void getPartsTest() {
        assertNotNull(detailsList.getParts());
        assertFalse(detailsList.getParts().isEmpty());
    }

    @org.junit.jupiter.api.Test
    void addPartTest() {
        detailsList.addPart(DetailTypes.BOLT);

        List<Part> parts = detailsList.getParts();
        assertTrue(parts.contains(new Part(DetailTypes.BOLT)));
    }

    @Test
    void getUniqueDetailsTest() {
        List<DetailTypes> uniqueTypes = detailsList.getUniqueDetailTypes();

        assertNotNull(uniqueTypes);
        assertFalse(uniqueTypes.isEmpty());

        assertTrue(uniqueTypes.contains(BOLT));
        assertTrue(uniqueTypes.contains(SCREW));
        assertTrue(uniqueTypes.contains(SHEET));
        assertTrue(uniqueTypes.contains(SPRING));
    }

    @Test
    void countDetailsTest() {
        List<String> counts = detailsList.countDetails();

        assertNotNull(counts);
        assertFalse(counts.isEmpty());

        for(String count : counts) {
            assertTrue(count.matches(".*: \\d+"));
        }
    }
}