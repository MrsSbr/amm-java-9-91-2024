package ru.vsu.amm.java;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.Sale;
import ru.vsu.amm.java.enums.Car;
import ru.vsu.amm.java.enums.Equipment;
import ru.vsu.amm.java.enums.Showroom;
import ru.vsu.amm.java.service.SalesService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.vsu.amm.java.service.SalesLogger.logger;

public class SalesServiceTest {

    @Test
    public void testFindMaxMarkup() {

        List<Sale> sales = new ArrayList<>();

        sales.add(new Sale(
                LocalDate.of(2023, 10, 1),
                Showroom.FRESH,
                Car.LADA_GRANTA,
                Equipment.BASIC,
                50000
        ));
        sales.add(new Sale(
                LocalDate.of(2023, 10, 5),
                Showroom.RINGAUTO,
                Car.HONDA_CIVIC,
                Equipment.STANDARD,
                70000
        ));
        sales.add(new Sale(
                LocalDate.of(2023, 10, 10),
                Showroom.MOTORLAND,
                Car.HONDA_CIVIC,
                Equipment.COMFORT,
                80000
        ));

        Map<Car, Showroom> result = new HashMap<>();
        result.put(Car.HONDA_CIVIC, Showroom.MOTORLAND);
        result.put(Car.LADA_GRANTA, Showroom.FRESH);

        assertEquals(SalesService.findMaxMarkup(sales), result);
        assertEquals(SalesService.findMaxMarkup(sales).size(), 2);

        logger.info("testFindMaxMarkup end");
    }

    @Test
    public void testFindBestShowroom() {
        List<Sale> sales = new ArrayList<>();

        sales.add(new Sale(
                LocalDate.of(2023, 10, 1),
                Showroom.FRESH,
                Car.LADA_GRANTA,
                Equipment.BASIC,
                50000
        ));
        sales.add(new Sale(
                LocalDate.of(2022, 10, 1),
                Showroom.FRESH,
                Car.LADA_GRANTA,
                Equipment.COMFORT,
                55000
        ));
        sales.add(new Sale(
                LocalDate.of(2022, 10, 1),
                Showroom.FRESH,
                Car.HONDA_CIVIC,
                Equipment.BASIC,
                60000
        ));
        sales.add(new Sale(
                LocalDate.of(2023, 10, 5),
                Showroom.RINGAUTO,
                Car.HONDA_CIVIC,
                Equipment.STANDARD,
                65000
        ));
        sales.add(new Sale(
                LocalDate.of(2023, 10, 5),
                Showroom.RINGAUTO,
                Car.HONDA_CIVIC,
                Equipment.BASIC,
                70000
        ));
        sales.add(new Sale(
                LocalDate.of(2023, 10, 10),
                Showroom.MOTORLAND,
                Car.FORD_FOCUS,
                Equipment.COMFORT,
                75000
        ));
        sales.add(new Sale(
                LocalDate.of(2012, 10, 10),
                Showroom.MOTORLAND,
                Car.HONDA_CIVIC,
                Equipment.COMFORT,
                80000
        ));

        assertEquals(SalesService.findBestShowroom(sales), Showroom.FRESH);

        logger.info("testFindBestShowroom end");
    }

    @Test
    public void testFindTotalMarkup() {
        List<Sale> sales = new ArrayList<>();

        sales.add(new Sale(
                LocalDate.of(2023, 10, 1),
                Showroom.FRESH,
                Car.LADA_GRANTA,
                Equipment.BASIC,
                50000
        ));
        sales.add(new Sale(
                LocalDate.of(2023, 10, 5),
                Showroom.RINGAUTO,
                Car.HONDA_CIVIC,
                Equipment.STANDARD,
                70000
        ));
        sales.add(new Sale(
                LocalDate.of(2023, 10, 10),
                Showroom.MOTORLAND,
                Car.HONDA_CIVIC,
                Equipment.COMFORT,
                50000
        ));
        sales.add(new Sale(
                LocalDate.of(2023, 10, 5),
                Showroom.RINGAUTO,
                Car.HONDA_CIVIC,
                Equipment.STANDARD,
                70000
        ));
        sales.add(new Sale(
                LocalDate.of(2012, 10, 10),
                Showroom.MOTORLAND,
                Car.HONDA_CIVIC,
                Equipment.COMFORT,
                100000
        ));

        Map<Showroom, Integer> result = new HashMap<>();
        result.put(Showroom.MOTORLAND, 150000);
        result.put(Showroom.RINGAUTO, 140000);
        result.put(Showroom.FRESH, 50000);

        assertEquals(SalesService.findTotalMarkup(sales), result);
        assertEquals(SalesService.findTotalMarkup(sales).size(), 3);

        logger.info("testFindTotalMarkup end");
    }
}