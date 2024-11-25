package ru.vsu.amm.java;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import ru.vsu.amm.java.entity.SaleRecord;
import ru.vsu.amm.java.analyzer.SalesAnalyzer;
import static java.time.LocalDate.of;
import static ru.vsu.amm.java.enums.Jewelry.Ring;
import static ru.vsu.amm.java.enums.Jewelry.Earrings;
import static ru.vsu.amm.java.enums.Jewelry.Necklace;
import static ru.vsu.amm.java.enums.Gemstone.Amber;
import static ru.vsu.amm.java.enums.Gemstone.Amethyst;
import static ru.vsu.amm.java.enums.Gemstone.Diamond;
import static ru.vsu.amm.java.enums.Gemstone.Emerald;
import static ru.vsu.amm.java.enums.Gemstone.Ruby;
import static ru.vsu.amm.java.enums.Gemstone.Sapphire;
import static ru.vsu.amm.java.enums.PreciousMetal.Silver;
import static ru.vsu.amm.java.enums.PreciousMetal.Gold;
import static ru.vsu.amm.java.enums.PreciousMetal.White_gold;


public class SaleRecordTests {

    private static final List<SaleRecord> emptyList = new ArrayList<>();
    private static final List<SaleRecord> listOfNull = null;
    private static final List<SaleRecord> listOfExamples = getSaleRecords();


    private static List<SaleRecord> getSaleRecords() {
        return new ArrayList<>(List.of(
                new SaleRecord(of(2024, 11, 11), Silver, Ring, Set.of(Amethyst), 12000),
                new SaleRecord(of(2021, 1, 8), Gold, Earrings, Set.of(Amber), 98000),
                new SaleRecord(of(2024, 5, 5),Silver, Ring, Set.of(Diamond), 150000),
                new SaleRecord(of(2024, 5, 10), Silver, Necklace, Set.of(Ruby, Emerald), 180000),
                new SaleRecord(of(2024, 6, 27), White_gold, Necklace, Set.of(Sapphire), 130000)
        ));
    }

    @Test
    public void testFindJewelryWithMostGemsOnExamples() {
        assertEquals(Necklace, SalesAnalyzer.findJewelryWithMostGems(listOfExamples));
    }

    @Test
    public void testFindJewelryWithMostGemsOnEmpty() {
        assertThrows(NoSuchElementException.class, () -> SalesAnalyzer.findJewelryWithMostGems(emptyList));
    }

    @Test
    public void testFindJewelryWithMostGemsOnNull() {
        assertThrows(NullPointerException.class, () -> SalesAnalyzer.findJewelryWithMostGems(listOfNull));
    }

    @Test
    public void testFindMonthWithLeastSilverSalesOnExamples() {
        assertEquals(YearMonth.of(2024, 11), SalesAnalyzer.findMonthWithLeastSilverSales(listOfExamples));
    }

    @Test
    public void testFindMonthWithLeastSilverSalesOnEmpty() {
        assertThrows(NoSuchElementException.class, () -> SalesAnalyzer.findMonthWithLeastSilverSales(emptyList));
    }

    @Test
    public void testFindMonthWithLeastSilverSalesOnNull() {
        assertThrows(NullPointerException.class, () -> SalesAnalyzer.findMonthWithLeastSilverSales(listOfNull));
    }

    @Test
    public void testFindGemsBySellingTimeOnExamples() {
        assertEquals(Set.of(Amber), SalesAnalyzer.findGemsBySellingTime(listOfExamples));
    }

    @Test
    public void testFindGemsBySellingTimeOnEmpty() {
        assertThrows(NoSuchElementException.class, () -> SalesAnalyzer.findGemsBySellingTime(emptyList));
    }

    @Test
    public void testFindGemsBySellingTimeOnNull() {
        assertThrows(NullPointerException.class, () -> SalesAnalyzer.findGemsBySellingTime(listOfNull));
    }

}