package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.Sacrifice;
import ru.vsu.amm.java.enums.SacrificeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.LocalDate;
import java.time.Year;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class SacrificeFactory {
    private final static Random rnd = new Random();

    private SacrificeFactory() {
    }

    public static Sacrifice generateSacrifice() {
        int year = rnd.nextInt(1300, 1522);
        int dayOfYear = rnd.nextInt(1, Year.isLeap(year) ? 367 : 366);
        LocalDate date = LocalDate.ofYearDay(year, dayOfYear);

        var sacrificeTypes = SacrificeType.values();
        int sacrificeTypeIndex = rnd.nextInt(sacrificeTypes.length);
        SacrificeType sacrificeType = sacrificeTypes[sacrificeTypeIndex];

        int daysUntilRain = rnd.nextInt(1, 101);

        return new Sacrifice(date, sacrificeType, daysUntilRain);
    }

    public static List<Sacrifice> generateSacrifice(int n) {
        return IntStream.range(0, n)
                .mapToObj(_ -> generateSacrifice())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
