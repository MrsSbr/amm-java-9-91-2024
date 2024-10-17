package ru.vsu.amm.java;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.Route;
import ru.vsu.amm.java.service.MostProfitableRouteServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class MostProfitableRouteImplTests {

    private static MostProfitableRouteServiceImpl mostProfitableRouteService;

    @BeforeAll
    public static void init() {
        mostProfitableRouteService = new MostProfitableRouteServiceImpl();
    }

    @Test
    public void testMostProfitableRoute() {
        // given
        Route expectedMostProfitableRoute = new Route(6, 55.0);
        List<Route> routes = List.of(
                new Route(1, 30.5),
                new Route(2, 33.0),
                new Route(3, 55.0),
                new Route(4, 30.5),
                new Route(5, 55.0),
                expectedMostProfitableRoute,
                new Route(7, 30.5),
                new Route(8, 55.0),
                new Route(9, 55.0),
                new Route(10, 30.5)
        );
        List<Double> profits = List.of(
                4148.0,
                5049.0,
                9350.0,
                5185.0,
                9350.0,
                9350.01,
                5185.0,
                8415.0,
                7480.0,
                3629.5
        );

        // when
        Route mostProfitableRoute = mostProfitableRouteService.mostProfitableRoute(routes, profits);

        //then
        assertEquals(expectedMostProfitableRoute, mostProfitableRoute);
    }

    @Test
    public void testMostProfitableRouteWhenEmpty() {
        // given
        List<Route> routes = List.of();
        List<Double> profits = List.of();

        // when & then
        assertThrows(
                NullPointerException.class,
                () -> mostProfitableRouteService.mostProfitableRoute(routes, profits)
        );
    }

    @Test
    public void testMostProfitableRouteWhenMoreProfits() {
        // given
        List<Route> routes = List.of(
                new Route(1, 30.5)
        );
        List<Double> profits = List.of(
                4148.0,
                9350.01
        );

        // when & then
        assertThrows(
                IndexOutOfBoundsException.class,
                () -> mostProfitableRouteService.mostProfitableRoute(routes, profits)
        );
    }
}
