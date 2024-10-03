package ru.vsu.amm.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.Sacrifice;
import ru.vsu.amm.java.enums.SacrificeType;
import ru.vsu.amm.java.service.SacrificeStats;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public final class SacrificeTests {

    private List<Sacrifice> records;

    @BeforeEach
    public void setup() {
        records = getSomeSacrifices();
    }

    @Test
    public void testGetNotConvictedPeople() {
        int instantRainCount = SacrificeStats.CountInstantRainSacrifices(records);

        assertEquals(2, instantRainCount);
    }

    @Test
    public void testGetPeopleMultipleArticles() {
        YearMonth lastMonthWithoutSacrifices = SacrificeStats.FindLastMonthWithoutSacrifices(records);

        assertEquals(YearMonth.of(1498, 12), lastMonthWithoutSacrifices);
    }

    @Test
    public void testPrintSacrificeStats() {
        String res = SacrificeStats.PrintSacrificeStats(records);

        assertEquals(
                """
                        Average days it took to rain after animal sacrifices: 5.0
                        Average days it took to rain after human sacrifices: 3.0
                        """,
                res
        );
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
