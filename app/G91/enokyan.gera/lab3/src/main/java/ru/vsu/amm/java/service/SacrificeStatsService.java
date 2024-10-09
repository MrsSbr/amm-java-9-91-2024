package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.Sacrifice;
import ru.vsu.amm.java.enums.SacrificeType;

import java.time.YearMonth;
import java.util.List;
import java.util.HashSet;
import java.util.Comparator;
import java.util.stream.Collectors;

public final class SacrificeStatsService {
    private SacrificeStatsService() {
    }

    public static int CountInstantRainSacrifices(List<Sacrifice> sacrificeList)
            throws NullPointerException {
        if (sacrificeList == null)
            throw new NullPointerException();

        return sacrificeList.stream()
                .reduce(0, (acc, s) -> acc + (s.daysUntilRain() == 1 ? 1 : 0), Integer::sum);
    }

    public static YearMonth FindLastMonthWithoutSacrifices(List<Sacrifice> sacrificeList)
            throws NullPointerException {
        if (sacrificeList == null)
            throw new NullPointerException();

        var yearMonthWithSacrifice = sacrificeList.stream()
                .map(s -> YearMonth.from(s.date()))
                .collect(Collectors.toCollection(HashSet::new));

        return yearMonthWithSacrifice.stream()
                .filter(s -> !yearMonthWithSacrifice.contains(s.minusMonths(1)))
                .max(Comparator.naturalOrder())
                .map(s -> s.minusMonths(1))
                .orElse(null);
    }

    public static double CompareHumanAndAnimalSacrificesEfficiency(List<Sacrifice> sacrificeList)
            throws NullPointerException, ArithmeticException {
        if (sacrificeList == null)
            throw new NullPointerException();

        var x = sacrificeList.stream()
                .collect(Collectors.groupingBy(Sacrifice::sacrificeType, Collectors.averagingInt(Sacrifice::daysUntilRain)));

        if (!x.containsKey(SacrificeType.Human) || !x.containsKey(SacrificeType.Animal))
            throw new ArithmeticException();

        return x.get(SacrificeType.Human) / x.get(SacrificeType.Animal) - 1;
    }
}