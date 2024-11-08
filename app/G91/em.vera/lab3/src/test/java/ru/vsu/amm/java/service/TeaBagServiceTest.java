package ru.vsu.amm.java.service;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.TeaBag;
import ru.vsu.amm.java.enums.TeaType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TeaBagServiceTest {
    private static final List<TeaBag> teaBags = getTeaBags();


    @Test
    void getBestYearsOfType() {
        int[] emptyBestYearsOfType = TeaBagService.getBestYearsOfType(new ArrayList<>());
        assertEquals(0, emptyBestYearsOfType.length);

        int[] bestYearsOfType = TeaBagService.getBestYearsOfType(teaBags);
        assertEquals(TeaType.values().length, bestYearsOfType.length);
        assertEquals(2018, bestYearsOfType[0]);
        assertEquals(2010, bestYearsOfType[1]);
        assertEquals(2023, bestYearsOfType[2]);
        assertEquals(2015, bestYearsOfType[3]);
    }

    @Test
    void getTeaTypesInYear() {
        List<TeaType> emptyTeaTypes = TeaBagService.getTeaTypesInYear(new ArrayList<>(), 2018);
        assertEquals(new ArrayList<TeaType>(), emptyTeaTypes);

        List<TeaType> teaTypes = TeaBagService.getTeaTypesInYear(teaBags, 2018);
        assertEquals(List.of(TeaType.GREEN, TeaType.WHITE), teaTypes);
    }

    @Test
    void getHaviestTeaBag() {
        int[] emptyHaviestTeaBags = TeaBagService.getHaviestTeaBag(new ArrayList<>());
        assertEquals(0, emptyHaviestTeaBags.length);

        int[] haviestTeaBags = TeaBagService.getHaviestTeaBag(teaBags);
        assertEquals(TeaType.values().length, haviestTeaBags.length);
        assertEquals(22, haviestTeaBags[0]);
        assertEquals(21, haviestTeaBags[1]);
        assertEquals(23, haviestTeaBags[2]);
        assertEquals(70, haviestTeaBags[3]);
    }

    private static List<TeaBag> getTeaBags() {
        List<TeaBag> teaBags = new ArrayList<>();
        teaBags.add(new TeaBag(TeaType.GREEN, 2018, 22));
        teaBags.add(new TeaBag(TeaType.GREEN, 2004, 20));
        teaBags.add(new TeaBag(TeaType.BLACK, 2010, 21));
        teaBags.add(new TeaBag(TeaType.OOLONG, 2023, 23));
        teaBags.add(new TeaBag(TeaType.WHITE, 2018, 20));
        teaBags.add(new TeaBag(TeaType.WHITE, 2015, 40));
        teaBags.add(new TeaBag(TeaType.WHITE, 2015, 70));
        return teaBags;
    }

}
