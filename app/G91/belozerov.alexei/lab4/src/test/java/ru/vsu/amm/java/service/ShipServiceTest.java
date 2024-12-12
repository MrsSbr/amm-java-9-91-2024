package ru.vsu.amm.java.service;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.enums.Nationality;
import ru.vsu.amm.java.enums.ShipType;
import ru.vsu.amm.java.model.Ship;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShipServiceTest {
    private static final List<Ship> ships = getShips();
    private static final List<Ship> emptyShips = new ArrayList<>();
    private static final List<Ship> nullShips = null;

    @Test
    void boundingShipCountByNationality() {
        int expected = 2;
        Map<Nationality, Long> result = ShipService.boundingShipCountByNationality(ships);

        assertTrue(result.containsKey(Nationality.BRITAIN)
                && result.get(Nationality.BRITAIN) == 1L);
        assertTrue(result.containsKey(Nationality.RUSSIA)
                && result.get(Nationality.RUSSIA) == 1L);
        assertEquals(expected, result.size());
    }

    @Test
    void emptyBoundingShipCountByNationality() {
        int expected = 0;
        assertEquals(expected, ShipService.boundingShipCountByNationality(emptyShips).size());
    }

    @Test
    void nullBoundingShipCountByNationality() {
        int expected = 0;
        assertEquals(expected, ShipService.boundingShipCountByNationality(nullShips).size());
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
        int expected = 2;

        List<Ship> result = ShipService.mostRumsStocks(ships);
        assertTrue(result.contains(ships.get(0)));
        assertTrue(result.contains(ships.get(2)));
        assertEquals(expected, result.size());
    }

    @Test
    void emptyMostRumsStocks() {
        int expected = 0;
        assertEquals(expected, ShipService.mostRumsStocks(emptyShips).size());
    }

    @Test
    void nullMostRumsStocks() {
        int expected = 0;
        assertEquals(expected, ShipService.mostRumsStocks(nullShips).size());
    }

    private static List<Ship> getShips() {
        return List.of(
                new Ship(LocalDate.of(2024, 11, 21), ShipType.FRIGATE, Nationality.BRITAIN, 1000, 100, true),
                new Ship(LocalDate.of(2020, 12, 12), ShipType.CARAVEL, Nationality.SPAIN, 1500, 50, false),
                new Ship(LocalDate.of(2021, 12, 31), ShipType.DRAKKAR, Nationality.RUSSIA, 500, 200, true)
        );
    }
}