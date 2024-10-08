package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.Sacrifice;
import ru.vsu.amm.java.enums.SacrificeType;

import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public final class SacrificeStatsService {
    private SacrificeStatsService() {
    }
    
    public static int CountInstantRainSacrifices(List<Sacrifice> sacrificeList) {
        return sacrificeList.stream()
                .reduce(0, (acc, s) -> acc + (s.daysUntilRain() == 1 ? 1 : 0), Integer::sum);
    }

    public static YearMonth FindLastMonthWithoutSacrifices(List<Sacrifice> sacrificeList) {
        var yearMonthWithSacrifice = sacrificeList.stream()
                .map(s -> YearMonth.from(s.date()))
                .collect(Collectors.toCollection(HashSet::new));

        return yearMonthWithSacrifice.stream()
                .filter(s -> !yearMonthWithSacrifice.contains(s.minusMonths(1)))
                .max(Comparator.naturalOrder())
                .map(s -> s.minusMonths(1))
                .orElse(null);
    }

    public static double CompareHumanAndAnimalSacrificesEfficiency(List<Sacrifice> sacrificeList) {
        var x = sacrificeList.stream()
                .reduce(
                        new ArrayList<>(Collections.nCopies(4, 0)),
                        (acc, s) -> {
                            int t = s.sacrificeType() == SacrificeType.Human ? 0 : 2;
                            acc.set(t, acc.get(t) + 1);
                            acc.set(t + 1, acc.get(t + 1) + s.daysUntilRain());
                            return acc;
                            },
                        (_, b) -> b
                );

        return ((double) x.get(1) / x.get(0)) / ((double) x.get(3) / x.get(2)) - 1;
    }
}