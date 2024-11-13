package ru.vsu.amm.java.service;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.BestTeaYear;
import ru.vsu.amm.java.entity.HaviestTeaBag;
import ru.vsu.amm.java.entity.TeaBag;
import ru.vsu.amm.java.enums.TeaType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TeaBagServiceTest {
    private static final List<TeaBag> teaBags = getTeaBags();


    @Test
    void getBestTeaYears() {
        List<BestTeaYear> bestTeaYears = TeaBagService.getBestTeaYears(teaBags);
        assertEquals(TeaType.values().length, bestTeaYears.size());

        assertTrue(bestTeaYears.stream().anyMatch(BestTeaYear -> BestTeaYear.teaType() == TeaType.GREEN && BestTeaYear.year() == 2018));
        assertTrue(bestTeaYears.stream().anyMatch(BestTeaYear -> BestTeaYear.teaType() == TeaType.BLACK && BestTeaYear.year() == 2010));
        assertTrue(bestTeaYears.stream().anyMatch(BestTeaYear -> BestTeaYear.teaType() == TeaType.WHITE && BestTeaYear.year() == 2015));
        assertTrue(bestTeaYears.stream().anyMatch(BestTeaYear -> BestTeaYear.teaType() == TeaType.OOLONG && BestTeaYear.year() == 2023));
    }

    @Test
    void emptyBestTeaYears() {
        List<BestTeaYear> emptyBestTeaYears = TeaBagService.getBestTeaYears(new ArrayList<>());
        assertEquals(0, emptyBestTeaYears.size());
    }

    @Test
    void nullBestTeaYears() {
        List<BestTeaYear> emptyBestTeaYears = TeaBagService.getBestTeaYears(null);
        assertEquals(0, emptyBestTeaYears.size());
    }

    @Test
    void getTeaTypesInYear() {
        List<TeaType> teaTypes = TeaBagService.getTeaTypesInYear(teaBags, 2018);
        assertEquals(List.of(TeaType.GREEN, TeaType.WHITE), teaTypes);
    }

    @Test
    void emptyTeaTypesInYear() {
        List<TeaType> emptyTeaTypes = TeaBagService.getTeaTypesInYear(new ArrayList<>(), 2018);
        assertEquals(new ArrayList<TeaType>(), emptyTeaTypes);
    }

    @Test
    void nullTeaTypesInYear() {
        List<TeaType> emptyTeaTypes = TeaBagService.getTeaTypesInYear(null, 2018);
        assertEquals(new ArrayList<TeaType>(), emptyTeaTypes);
    }

    @Test
    void getHaviestTeaBag() {
        List<HaviestTeaBag> haviestTeaBags = TeaBagService.getHaviestTeaBag(teaBags);

        assertEquals(TeaType.values().length, haviestTeaBags.size());
        assertTrue(haviestTeaBags.stream().anyMatch(HaviestTeaBag -> HaviestTeaBag.teaType() == TeaType.GREEN && HaviestTeaBag.weight() == 22));
        assertTrue(haviestTeaBags.stream().anyMatch(HaviestTeaBag -> HaviestTeaBag.teaType() == TeaType.BLACK && HaviestTeaBag.weight() == 21));
        assertTrue(haviestTeaBags.stream().anyMatch(HaviestTeaBag -> HaviestTeaBag.teaType() == TeaType.WHITE && HaviestTeaBag.weight() == 70));
        assertTrue(haviestTeaBags.stream().anyMatch(HaviestTeaBag -> HaviestTeaBag.teaType() == TeaType.OOLONG && HaviestTeaBag.weight() == 23));
    }

    @Test
    void emptyHaviestTeaBag() {
        List<HaviestTeaBag> emptyHaviestTeaBags = TeaBagService.getHaviestTeaBag(new ArrayList<>());
        assertEquals(0, emptyHaviestTeaBags.size());
    }

    @Test
    void nullHaviestTeaBag() {
        List<HaviestTeaBag> emptyHaviestTeaBags = TeaBagService.getHaviestTeaBag(null);
        assertEquals(0, emptyHaviestTeaBags.size());
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
