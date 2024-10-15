package ru.vsu.amm.java;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AncientWallTest {
    private static AncientWall ancientWall;

    @BeforeAll
    public static void initAncientWall() {
        ArrayList<AncientRecord> records = new ArrayList<>();

        records.add(new AncientRecord(
                LocalDate.of(-39999, 1, 2),
                200,
                HunterName.Booga
        ));
        records.add(new AncientRecord(
                LocalDate.of(-39998, 3, 4),
                230,
                HunterName.Booga
        ));
        records.add(new AncientRecord(
                LocalDate.of(-31004, 5, 6),
                499,
                HunterName.Wooga
        ));
        records.add(new AncientRecord(
                LocalDate.of(-35555, 7, 8),
                175,
                HunterName.Googa
        ));
        records.add(new AncientRecord(
                LocalDate.of(-34343, 9, 10),
                333,
                HunterName.Ooga
        ));

        ancientWall = new AncientWall(records);
    }

    @Test
    public void findHunterNamesTest() {
        List<HunterName> hunterNames = ancientWall.findHunterNames();

        assertNotNull(hunterNames);

        assertTrue(hunterNames.contains(HunterName.Ooga));
        assertTrue(hunterNames.contains(HunterName.Booga));
        assertTrue(hunterNames.contains(HunterName.Googa));
        assertTrue(hunterNames.contains(HunterName.Wooga));

        assertFalse(hunterNames.contains(HunterName.Dooga));
        assertFalse(hunterNames.contains(HunterName.Hooga));
        assertFalse(hunterNames.contains(HunterName.Mooga));
    }

    @Test
    public void sumRecentMammothWeightTest() {
        LocalDate date = LocalDate.of(-39997, 11, 12);
        int sumRecentMammothWeight = ancientWall.sumRecentMammothWeight(date);

        assertEquals(200 + 230, sumRecentMammothWeight);
    }

    @Test
    public void countMammothsForEachHunterTest() {
        Map<HunterName, Long> hunters = ancientWall.countMammothsForEachHunter();

        assertNotNull(hunters);

        assertTrue(hunters.containsKey(HunterName.Ooga));
        assertTrue(hunters.containsKey(HunterName.Booga));
        assertTrue(hunters.containsKey(HunterName.Googa));
        assertTrue(hunters.containsKey(HunterName.Wooga));

        assertFalse(hunters.containsKey(HunterName.Dooga));
        assertFalse(hunters.containsKey(HunterName.Hooga));
        assertFalse(hunters.containsKey(HunterName.Mooga));

        assertEquals(hunters.get(HunterName.Ooga), 1);
        assertEquals(hunters.get(HunterName.Booga), 2);
        assertEquals(hunters.get(HunterName.Googa), 1);
        assertEquals(hunters.get(HunterName.Wooga), 1);
    }
}
