package ru.vsu.amm.java.analyzer;

import ru.vsu.amm.java.entity.SaleRecord;
import ru.vsu.amm.java.enums.Gemstone;
import ru.vsu.amm.java.enums.Jewelry;
import ru.vsu.amm.java.enums.PreciousMetal;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

public class SalesAnalyzer {

    public static Jewelry findJewelryWithMostGems(List<SaleRecord> saleRecordList) {
        return saleRecordList.stream()
                .collect(Collectors.groupingBy(
                        SaleRecord::getJewelry,
                        Collectors.summingInt(record -> record.getGemstones().size())
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(()-> new NoSuchElementException("oops! no such jewelry type found!"));
    }

    public static YearMonth findMonthWithLeastSilverSales(List<SaleRecord> saleRecordList) {
        return saleRecordList.stream()
                .filter(record -> record.getPreciousMetal() == PreciousMetal.Silver)
                .collect(Collectors.groupingBy(
                        record -> YearMonth.from(record.getDate()),
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new NoSuchElementException("oops! no silver jewelry sales found at all!"));
    }

    public static Set<Gemstone> findGemsBySellingTime(List<SaleRecord> saleRecordList) {
        LocalDate threeYearsAgoStart = LocalDate.now().minusYears(3).withDayOfYear(1);
        LocalDate threeYearsAgoEnd = LocalDate.now().minusYears(2).withDayOfYear(1).minusDays(1);
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);

        if (saleRecordList.isEmpty()) {
            throw new NoSuchElementException("oops! no sales found at all!");
        }

        Set<Gemstone> gemsSoldThreeYearsAgo = saleRecordList.stream()
                .filter(record -> !record.getDate().isBefore(threeYearsAgoStart)
                        && !record.getDate().isAfter(threeYearsAgoEnd))
                .flatMap(record -> record.getGemstones().stream())
                .collect(Collectors.toSet());

        Set<Gemstone> gemsSoldLastSixMonths = saleRecordList.stream()
                .filter(record -> !record.getDate().isBefore(sixMonthsAgo))
                .flatMap(record -> record.getGemstones().stream())
                .collect(Collectors.toSet());

        gemsSoldThreeYearsAgo.removeAll(gemsSoldLastSixMonths);
        return gemsSoldThreeYearsAgo;

    }

}
