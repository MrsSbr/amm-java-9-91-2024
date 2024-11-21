package ru.vsu.amm.java.service;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.enums.Nationality;
import ru.vsu.amm.java.enums.ShipType;
import ru.vsu.amm.java.model.Ship;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ShipServiceTest {
    private static final List<Ship> ships = getShips();
    private static final List<Ship> emptyShips = new ArrayList<>();
    private static final List<Ship> nullShips = null;

    @Test
    void boundingShipCountByNationality() {
        Map<Nationality, Long> expected = new HashMap<>();
        expected.put(Nationality.RUSSIA, 1L);
        expected.put(Nationality.BRITAIN, 1L);
        assertEquals(expected, ShipService.boundingShipCountByNationality(ships));
    }

    @Test
    void emptyBoundingShipCountByNationality() {
        Map<Nationality, Long> expected = new HashMap<>();
        assertEquals(expected, ShipService.boundingShipCountByNationality(emptyShips));
    }

    @Test
    void nullBoundingShipCountByNationality() {
        Map<Nationality, Long> expected = new HashMap<>();
        assertEquals(expected, ShipService.boundingShipCountByNationality(nullShips));
    }

    @Test
    void lessProfitMonth() {
        Month expected = Month.NOVEMBER;
        assertEquals(expected, ShipService.lessProfitMonth(ships));
    }

    @Test
    void emptyLessProfitMonth() {
        Month expected = Month.JANUARY;
        assertEquals(expected, ShipService.lessProfitMonth(emptyShips));
    }

    @Test
    void nullLessProfitMonth() {
        Month expected = Month.JANUARY;
        assertEquals(expected, ShipService.lessProfitMonth(nullShips));
    }

    @Test
    void mostRumsStocks() {
        List<Ship> expected = new ArrayList<>();
        expected.add(ships.get(2));
        expected.add(ships.get(0));
        assertEquals(expected, ShipService.mostRumsStocks(ships));
    }

    @Test
    void emptyMostRumsStocks() {
        List<Ship> expected = new ArrayList<>();
        assertEquals(expected, ShipService.mostRumsStocks(emptyShips));
    }

    @Test
    void nullMostRumsStocks() {
        List<Ship> expected = new ArrayList<>();
        assertEquals(expected, ShipService.mostRumsStocks(nullShips));
    }

    private static List<Ship> getShips() {
        return List.of(
                new Ship(LocalDate.of(2024, 11, 21), ShipType.FRIGATE, Nationality.BRITAIN, 1000, 100, true),
                new Ship(LocalDate.of(2020, 12, 12), ShipType.CARAVEL, Nationality.SPAIN, 1500, 50, false),
                new Ship(LocalDate.of(2021, 12, 31), ShipType.DRAKKAR, Nationality.RUSSIA, 500, 200, true)
        );
    }
}