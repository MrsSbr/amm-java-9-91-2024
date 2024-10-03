package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.Sacrifice;
import ru.vsu.amm.java.enums.SacrificeType;

import java.util.Random;
import java.time.LocalDate;
import java.time.Year;

public final class SacrificeFactory {
    private final static Random random = new Random();

    private SacrificeFactory() {
    }

    public static Sacrifice generateSacrifice() {
        int year = random.nextInt(1300, 1522);
        return new Sacrifice(
                LocalDate.ofYearDay(year, random.nextInt(1, Year.isLeap(year) ? 367 : 366)),
                random.nextBoolean() ? SacrificeType.Human : SacrificeType.Animal,
                random.nextInt(1,101)
        );
    }
}
