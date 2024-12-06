package ru.vsu.amm.java.services;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DealsAnalyzerTest {
    @Test
    void testMostEffectiveManager() {
        List<Deal> deals = List.of(
                new Deal("Alice", "Client1", 500.0, LocalDate.now().minusDays(10)),
                new Deal("Bob", "Client2", 1000.0, LocalDate.now().minusDays(20)),
                new Deal("Alice", "Client3", 300.0, LocalDate.now().minusMonths(2))
        );

        String result = DealsAnalyzer.findMostEffectiveManagerLastMonth(deals);
        assertEquals("Bob", result);
    }

    @Test
    void testMostEffectiveManageNull() {
        List<Deal> deals = List.of();

        String result = DealsAnalyzer.findMostEffectiveManagerLastMonth(deals);
        assertEquals("No data", result);
    }

    @Test
    void testIncomeByClients() {
        List<Deal> deals = List.of(
                new Deal("Alice", "Client1", 500.0, LocalDate.now()),
                new Deal("Alice", "Client1", 300.0, LocalDate.now()),
                new Deal("Bob", "Client2", 1000.0, LocalDate.now())
        );

        Map<String, Double> result = DealsAnalyzer.collectIncomeByClients(deals);
        assertEquals(800.0, result.get("Client1"));
        assertEquals(1000.0, result.get("Client2"));
    }

    @Test
    void testIncomeByClientsNull() {
        List<Deal> deals = List.of();

        Map<String, Double> result = DealsAnalyzer.collectIncomeByClients(deals);
        assertEquals(0, result.size());
    }

    @Test
    void testMostProfitableMonth() {
        List<Deal> deals = List.of(
                new Deal("Alice", "Client1", 500.0, LocalDate.of(2024, 10, 1)),
                new Deal("Alice", "Client1", 1000.0, LocalDate.of(2024, 10, 15)),
                new Deal("Bob", "Client2", 200.0, LocalDate.of(2024, 11, 1))
        );

        Month result = DealsAnalyzer.findMostProfitableMonth(deals);
        assertEquals(Month.OCTOBER, result);
    }

    @Test
    void testMostProfitableMonthNull() {
        List<Deal> deals = List.of();

        Month result = DealsAnalyzer.findMostProfitableMonth(deals);
        assertNull(result);
    }
}