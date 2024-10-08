package ru.vsu.amm.java;

import org.junit.jupiter.api.BeforeAll;
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

    private static List<Sacrifice> sacrificeList;

    @BeforeAll
    public static void setup() {
        sacrificeList = getSomeSacrifices();
    }

    @Test
    public void testCountInstantRainSacrifices() {
        assertEquals(2, SacrificeStatsService.CountInstantRainSacrifices(sacrificeList));
    }

    @Test
    public void testFindLastMonthWithoutSacrifices() {
        assertEquals(YearMonth.of(1498, 12), SacrificeStatsService.FindLastMonthWithoutSacrifices(sacrificeList));
    }

    @Test
    public void testCompareHumanAndAnimalSacrificesEfficiency() {
        assertEquals(-0.4, SacrificeStatsService.CompareHumanAndAnimalSacrificesEfficiency(sacrificeList));
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
