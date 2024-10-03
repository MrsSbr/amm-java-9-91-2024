package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.Sacrifice;
import ru.vsu.amm.java.enums.SacrificeType;

import java.time.Period;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public final class SacrificeStatsService {
    private final List<Sacrifice> sacrificeList;
    private int instantRainSacrificeCount;
    private YearMonth lastMonthWithoutSacrifices;
    private double animalSacrificeRatio;
    private double humanSacrificeRatio;

    public SacrificeStatsService(List<Sacrifice> sacrificeList) {
        this.sacrificeList = sacrificeList;
    }

    private int CountInstantRainSacrifices() {
        int res = 0;

        for (Sacrifice i : sacrificeList) {
            if (i.daysUntilRain() == 1) {
                ++res;
            }
        }

        return res;
    }

    private YearMonth FindMin() {
        YearMonth min = YearMonth.from(sacrificeList.getFirst().date());

        for (Sacrifice i : sacrificeList) {
            YearMonth potential = YearMonth.from(i.date());
            if (potential.isBefore(min)) {
                min = potential;
            }
        }

        return min;
    }

    private YearMonth FindMax() {
        YearMonth max = YearMonth.from(sacrificeList.getFirst().date());

        for (Sacrifice i : sacrificeList) {
            YearMonth potential = YearMonth.from(i.date());
            if (potential.isAfter(max)) {
                max = potential;
            }
        }

        return max;
    }

    private YearMonth FindLastMonthWithoutSacrifices() {
        YearMonth min = FindMin();
        YearMonth max = FindMax();

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

    private double GetAnimalSacrificeStats() {
        int animalCount = 0;
        int animalDays = 0;

        for (Sacrifice i : sacrificeList) {
            if (i.sacrificeType() == SacrificeType.Animal) {
                ++animalCount;
                animalDays += i.daysUntilRain();
            }
        }

        return (double) animalDays / animalCount;
    }

    private double GetHumanSacrificeStats() {
        int humanCount = 0;
        int humanDays = 0;

        for (Sacrifice i : sacrificeList) {
            if (i.sacrificeType() == SacrificeType.Human) {
                ++humanCount;
                humanDays += i.daysUntilRain();
            }
        }

        return (double) humanDays / humanCount;
    }

    public void CollectStats() {
        instantRainSacrificeCount = CountInstantRainSacrifices();
        lastMonthWithoutSacrifices = FindLastMonthWithoutSacrifices();
        animalSacrificeRatio = GetAnimalSacrificeStats();
        humanSacrificeRatio = GetHumanSacrificeStats();
    }

    public void PrintStats() {
        System.out.println("Sacrifices with instant rain: " + instantRainSacrificeCount);
        System.out.println("Last month without sacrifices: " + lastMonthWithoutSacrifices);
        System.out.println("Average days it took to rain after animal sacrifices: " + animalSacrificeRatio);
        System.out.println("Average days it took to rain after human sacrifices: " + humanSacrificeRatio);
    }
}
