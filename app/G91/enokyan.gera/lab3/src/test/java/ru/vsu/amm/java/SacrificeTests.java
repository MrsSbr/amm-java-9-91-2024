package ru.vsu.amm.java;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.Sacrifice;
import ru.vsu.amm.java.enums.SacrificeType;
import ru.vsu.amm.java.service.SacrificeStatsService;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public final class SacrificeTests {

    private static final List<Sacrifice> nullSacrificeList = null;
    private static final List<Sacrifice> emptySacrificeList = new ArrayList<>();
    private static final List<Sacrifice> fullSacrificeList = getSomeSacrifices();

    @Test
    public void testCountInstantRainSacrificesWithNull() {
        assertThrows(NullPointerException.class, () -> SacrificeStatsService.countInstantRainSacrifices(nullSacrificeList));
    }

    @Test
    public void testCountInstantRainSacrificesWithEmptyList() {
        assertEquals(0, SacrificeStatsService.countInstantRainSacrifices(emptySacrificeList));
    }

    @Test
    public void testCountInstantRainSacrificesWithFullList() {
        assertEquals(2, SacrificeStatsService.countInstantRainSacrifices(fullSacrificeList));
    }

    @Test
    public void testFindLastMonthWithoutSacrificesWithNull() {
        assertThrows(NullPointerException.class, () -> SacrificeStatsService.findLastMonthWithoutSacrifices(nullSacrificeList));
    }

    @Test
    public void testFindLastMonthWithoutSacrificesWithEmptyList() {
        assertThrows(NullPointerException.class, () -> SacrificeStatsService.findLastMonthWithoutSacrifices(emptySacrificeList));
    }

    @Test
    public void testFindLastMonthWithoutSacrificesWithFullList() {
        assertEquals(YearMonth.of(1498, 12), SacrificeStatsService.findLastMonthWithoutSacrifices(fullSacrificeList));
    }

    @Test
    public void testCompareHumanAndAnimalSacrificesEfficiencyWithNull() {
        assertThrows(NullPointerException.class, () -> SacrificeStatsService.compareHumanAndAnimalSacrificesEfficiency(nullSacrificeList));
    }

    @Test
    public void testCompareHumanAndAnimalSacrificesEfficiencyWithEmptyList() {
        assertThrows(ArithmeticException.class, () -> SacrificeStatsService.compareHumanAndAnimalSacrificesEfficiency(emptySacrificeList));
    }

    @Test
    public void testCompareHumanAndAnimalSacrificesEfficiencyWithFullList() {
        assertEquals(-0.4, SacrificeStatsService.compareHumanAndAnimalSacrificesEfficiency(fullSacrificeList));
    }

    private static List<Sacrifice> getSomeSacrifices() {
        return new ArrayList<>(List.of(
            new Sacrifice(LocalDate.of(1499, 1, 1), SacrificeType.Human, 1),
            new Sacrifice(LocalDate.of(1444, 3, 2), SacrificeType.Human, 2),
            new Sacrifice(LocalDate.of(1444, 3, 2), SacrificeType.Human, 3),
            new Sacrifice(LocalDate.of(1444, 3, 2), SacrificeType.Human, 4),
            new Sacrifice(LocalDate.of(1444, 3, 2), SacrificeType.Human, 5),
            new Sacrifice(LocalDate.of(1444, 3, 2), SacrificeType.Animal, 1),
            new Sacrifice(LocalDate.of(1444, 3, 2), SacrificeType.Animal, 9),
            new Sacrifice(LocalDate.of(1444, 3, 2), SacrificeType.Animal, 5),
            new Sacrifice(LocalDate.of(1444, 3, 2), SacrificeType.Animal, 5),
            new Sacrifice(LocalDate.of(1444, 3, 2), SacrificeType.Animal, 5)
        ));
    }
}
