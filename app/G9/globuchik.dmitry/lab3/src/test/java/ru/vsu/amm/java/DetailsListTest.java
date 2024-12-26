package ru.vsu.amm.java;

//import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static ru.vsu.amm.java.DetailTypes.*;
import static ru.vsu.amm.java.DetailsService.*;

class DetailsListTest {

    private final List<Part> detailsList = new LinkedList<>();

    @BeforeEach
    void setup() {

        Random rand = new Random();
        int count = rand.nextInt(1000) + 1;
        for (int i = 0; i < count; ++i) {
            addPart(DetailTypes.getRandomType(), detailsList);
        }

    }

    @org.junit.jupiter.api.Test
    void getPartsTest() {
        assertNotNull(detailsList);
        assertFalse(detailsList.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void addPartTest() {
        addPart(DetailTypes.BOLT, detailsList);
        assertTrue(detailsList.contains(new Part(DetailTypes.BOLT)));
    }

    @Test
    void getUniqueDetailsTest() {
        Set<DetailTypes> uniqueTypes = getUniqueDetailTypes(detailsList);

        assertNotNull(uniqueTypes);
        assertFalse(uniqueTypes.isEmpty());

        assertTrue(uniqueTypes.contains(BOLT));
        assertTrue(uniqueTypes.contains(SCREW));
        assertTrue(uniqueTypes.contains(SHEET));
        assertTrue(uniqueTypes.contains(SPRING));
    }

    @Test
    void countDetailsTest() {
        List<String> counts = countDetails(detailsList);

        assertNotNull(counts);
        assertFalse(counts.isEmpty());

        for(String count : counts) {
            assertTrue(count.matches(".*: \\d+"));
        }
    }
}