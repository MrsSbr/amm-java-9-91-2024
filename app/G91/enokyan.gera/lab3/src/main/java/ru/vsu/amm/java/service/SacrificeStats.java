package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.Sacrifice;
import ru.vsu.amm.java.enums.SacrificeType;

import java.time.Period;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public final class SacrificeStats {
    private SacrificeStats() {
    }

    private static int GoCount(List<Sacrifice> sacrificeList, int acc) {
        if (sacrificeList.isEmpty()) {
            return acc;
        } else {
            return GoCount(
                    sacrificeList.subList(1, sacrificeList.size()),
                    acc + (sacrificeList.getFirst().daysUntilRain() == 1 ? 1 : 0)
            );
        }
    }

    public static int CountInstantRainSacrifices(List<Sacrifice> sacrificeList) {
        return GoCount(sacrificeList, 0);
    }

    private static YearMonth GoFindMax(List<Sacrifice> sacrificeList, YearMonth max) {
        if (sacrificeList.isEmpty()) {
            return max;
        } else {
            YearMonth i = YearMonth.from(sacrificeList.getFirst().date());
            return GoFindMax(sacrificeList.subList(1, sacrificeList.size()), i.isAfter(max) ? i : max);
        }
    }

    private static YearMonth GoFindMin(List<Sacrifice> sacrificeList, YearMonth min) {
        if (sacrificeList.isEmpty()) {
            return min;
        } else {
            YearMonth i = YearMonth.from(sacrificeList.getFirst().date());
            return GoFindMin(sacrificeList.subList(1, sacrificeList.size()), i.isBefore(min) ? i : min);
        }
    }

    public static YearMonth FindLastMonthWithoutSacrifices(List<Sacrifice> sacrificeList) {
        YearMonth max = GoFindMax(sacrificeList, YearMonth.from(sacrificeList.getFirst().date()));
        YearMonth min = GoFindMin(sacrificeList, YearMonth.from(sacrificeList.getFirst().date()));

        int minMonth = min.getYear() * 12 + min.getMonthValue() - 1;
        int maxMonth = max.getYear() * 12 + max.getMonthValue() - 1;

        int size = maxMonth - minMonth + 1;

        List<Boolean> x = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            x.add(false);
        }

        for (Sacrifice i : sacrificeList) {
            YearMonth tmp = YearMonth.from(i.date());
            x.set(tmp.getYear() * 12 + tmp.getMonthValue() - minMonth - 1, true);
        }

        for (int i = size - 1; i >= 0; --i) {
            if (!x.get(i)) {
                return YearMonth.of((minMonth + i) / 12, (minMonth + i) % 12 + 1);
            }
        }

        return min.minus(Period.ofMonths(1));
    }

    public static String PrintSacrificeStats(List<Sacrifice> sacrificeList) {
        int animalCount = 0;
        int animalDays = 0;
        int humanCount = 0;
        int humanDays = 0;

        for (Sacrifice i : sacrificeList) {
            switch (i.sacrificeType()) {
                case SacrificeType.Animal -> {
                    ++animalCount;
                    animalDays += i.daysUntilRain();
                }
                case SacrificeType.Human -> {
                    ++humanCount;
                    humanDays += i.daysUntilRain();
                }
            }
        }

        return "Average days it took to rain after animal sacrifices: " + (double) animalDays / animalCount +
        "\nAverage days it took to rain after human sacrifices: " + (double) humanDays / humanCount + '\n';
    }

//    private static void GoPrintStats(
//            List<Sacrifice> sacrificeList,
//            int animalCount,
//            int animalDays,
//            int humanCount,
//            int humanDays) {
//        if (sacrificeList.isEmpty()) {
//            System.out.println("Average days it took to rain after animal sacrifices: " + (double) animalDays / animalCount);
//            System.out.println("Average days it took to rain after human sacrifices: " + (double) humanDays / humanCount);
//        } else {
//            Sacrifice head = sacrificeList.getFirst();
//            switch (head.sacrificeType()) {
//                case SacrificeType.Animal -> GoPrintStats(
//                        sacrificeList.subList(1, sacrificeList.size()),
//                        animalCount + 1,
//                        animalDays + head.daysUntilRain(),
//                        humanCount,
//                        humanDays
//                );
//                case SacrificeType.Human -> GoPrintStats(
//                        sacrificeList.subList(1, sacrificeList.size()),
//                        animalCount,
//                        animalDays,
//                        humanCount + 1,
//                        humanDays + head.daysUntilRain()
//                );
//            }
//        }
//    }
//
//    public static void PrintSacrificeStats(List<Sacrifice> sacrificeList) {
//        GoPrintStats(sacrificeList, 0, 0, 0, 0);
//    }
}
