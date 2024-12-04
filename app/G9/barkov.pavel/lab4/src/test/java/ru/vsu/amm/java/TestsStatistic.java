package ru.vsu.amm.java;


import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TestsStatistic {
    public TestsStatistic() {
    }

    @Test
    public void testMostSuccessManager() {
        List<Deal> deals = List.of(
                new Deal("Manager1", "Client1", 50D, LocalDate.of(2020, 1, 1)),
                new Deal("Manager2", "Client1", 10D, LocalDate.of(2020, 1, 1)),
                new Deal("Manager3", "Client1", 20D, LocalDate.of(2020, 1, 1)),
                new Deal("Manager4", "Client1", 30D, LocalDate.of(2020, 1, 1)),
                new Deal("Manager5", "Client1", 40D, LocalDate.of(2020, 1, 1)));
        var res = Statistic.mostSuccessManager(deals);
        Assertions.assertNotNull(res);
        Assertions.assertTrue(res.contains("Manager1"));
        Assertions.assertFalse(res.size() > 1);
    }

    @Test
    public void testIncomeFromClients() {
        List<Deal> deals = List.of(
                new Deal("Manager1", "Client2", 5000D, LocalDate.of(2020, 1, 1)),
                new Deal("Manager2", "Client1", 1000D, LocalDate.of(2020, 1, 1)),
                new Deal("Manager3", "Client1", 2000D, LocalDate.of(2020, 1, 1)),
                new Deal("Manager4", "Client1", 3000D, LocalDate.of(2020, 1, 1)),
                new Deal("Manager5", "Client1", 4000D, LocalDate.of(2020, 1, 1)));
        var res = Statistic.incomeFromClients(deals);
        Assertions.assertNotNull(res);
        Assertions.assertTrue(res.containsKey("Client2") && res.get("Client2").equals(5000D));
        Assertions.assertTrue(res.containsKey("Client1") && res.get("Client1").equals(10000D));
        Assertions.assertFalse(res.size() > 2);
    }

    @Test
    public void testMostSuccessMonth() {
        List<Deal> deals = List.of(
                new Deal("Manager1", "Client2", 5000D, LocalDate.of(2020, 1, 1)),
                new Deal("Manager2", "Client1", 1000D, LocalDate.of(2020, 2, 1)),
                new Deal("Manager3", "Client1", 2000D, LocalDate.of(2020, 3, 1)),
                new Deal("Manager4", "Client1", 3000D, LocalDate.of(2020, 4, 1)),
                new Deal("Manager5", "Client1", 4000D, LocalDate.of(2020, 5, 1)));
        var res = Statistic.mostSuccessMonth(deals);
        Assertions.assertNotNull(res);
        Assertions.assertTrue(res.contains(Month.JANUARY));
        Assertions.assertFalse(res.size() > 1);
    }

    @Test
    public void nullTestMostSuccessManager() {
        var res = Statistic.mostSuccessManager(new ArrayList<>());
        Assertions.assertEquals(res.size(), 0);
    }

    @Test
    public void nullTestIncomeFromClients() {
        var res = Statistic.incomeFromClients(new ArrayList<>());
        Assertions.assertEquals(res.size(), 0);
    }

    @Test
    public void nullTestMostSuccessMonth() {
        var res = Statistic.mostSuccessMonth(new ArrayList<>());
        Assertions.assertEquals(res.size(), 0);
    }
}
