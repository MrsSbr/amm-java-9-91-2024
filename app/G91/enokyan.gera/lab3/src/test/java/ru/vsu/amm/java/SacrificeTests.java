package ru.vsu.amm.java;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.Sacrifice;
import ru.vsu.amm.java.enums.SacrificeType;
import ru.vsu.amm.java.service.SacrificeStatsService;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public final class SacrificeTests {

    private static SacrificeStatsService sacrificeStatsService;

    @BeforeAll
    public static void setup() {
        List<Sacrifice> sacrificeList = getSomeSacrifices();
        sacrificeStatsService = new SacrificeStatsService(sacrificeList);
    }

    @Test
    public void testSacrificeStatsService() throws NoSuchFieldException, IllegalAccessException {
        sacrificeStatsService.CollectStats();

        Field instantRainSacrificeCountField = SacrificeStatsService.class.getDeclaredField("instantRainSacrificeCount");
        instantRainSacrificeCountField.setAccessible(true);
        int instantRainSacrificeCount = (int)instantRainSacrificeCountField.get(sacrificeStatsService);

        Field lastMonthWithoutSacrificesField = SacrificeStatsService.class.getDeclaredField("lastMonthWithoutSacrifices");
        lastMonthWithoutSacrificesField.setAccessible(true);
        YearMonth lastMonthWithoutSacrifices = (YearMonth)lastMonthWithoutSacrificesField.get(sacrificeStatsService);

        Field animalSacrificeRatioField = SacrificeStatsService.class.getDeclaredField("animalSacrificeRatio");
        animalSacrificeRatioField.setAccessible(true);
        double animalSacrificeRatio = (double)animalSacrificeRatioField.get(sacrificeStatsService);

        Field humanSacrificeRatioField = SacrificeStatsService.class.getDeclaredField("humanSacrificeRatio");
        humanSacrificeRatioField.setAccessible(true);
        double humanSacrificeRatio = (double)humanSacrificeRatioField.get(sacrificeStatsService);

        assertEquals(2, instantRainSacrificeCount);
        assertEquals(YearMonth.of(1498, 12), lastMonthWithoutSacrifices);
        assertEquals(5, animalSacrificeRatio);
        assertEquals(3, humanSacrificeRatio);
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
