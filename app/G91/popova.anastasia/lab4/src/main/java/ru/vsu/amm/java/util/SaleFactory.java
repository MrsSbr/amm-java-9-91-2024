package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.SaleRecord;
import ru.vsu.amm.java.enums.Gemstone;
import ru.vsu.amm.java.enums.Jewelry;
import ru.vsu.amm.java.enums.PreciousMetal;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;


public class SaleFactory {

    private static final Random random = new Random();

    private SaleFactory() {
    }

    public static SaleRecord generateSaleRecord() {
        int year = random.nextInt(2015, LocalDate.now().getYear() + 1);
        int month = random.nextInt(1, 13);
        int day = random.nextInt(1, LocalDate.of(year, month, 1).lengthOfMonth() + 1);
        LocalDate date = LocalDate.of(year, month, day);

        var metalTypes = PreciousMetal.values();
        int metalIndex = random.nextInt(metalTypes.length + 1);
        PreciousMetal preciousMetal = metalTypes[metalIndex];

        var jewelryTypes = Jewelry.values();
        int jewelryIndex = random.nextInt(jewelryTypes.length + 1);
        Jewelry jewelry = jewelryTypes[jewelryIndex];

        var gemstoneTypes = Gemstone.values();
        int gemstoneAmount = random.nextInt(1, gemstoneTypes.length + 1);
        Set<Gemstone> gemstones = EnumSet.noneOf(Gemstone.class);
        while (gemstones.size() < gemstoneAmount) {
            Gemstone randomGemstone = gemstoneTypes[random.nextInt(gemstoneTypes.length + 1)];
            gemstones.add(randomGemstone);
        }

        int price = random.nextInt(10000, 3000001);

        return new SaleRecord(date, preciousMetal, jewelry, gemstones, price);
    }

    public static List<SaleRecord> generateSaleRecord(int q) {
        return IntStream.range(0, q)
                .mapToObj(i -> generateSaleRecord())
                .toList();
    }

}