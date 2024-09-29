package ru.vsu.amm.java;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.Driver;
import ru.vsu.amm.java.entity.Route;
import ru.vsu.amm.java.mocks.MostProfitableRouteServiceMock;
import ru.vsu.amm.java.mocks.PassengersCountServiceMock;
import ru.vsu.amm.java.mocks.StorageServiceMock;
import ru.vsu.amm.java.service.StatsServiceImpl;
import ru.vsu.amm.java.util.DriverScheduleGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public final class StatsServiceImplTests {

    private static StatsServiceImpl statsService;
    private static StorageServiceMock storageService;
    private static PassengersCountServiceMock passengersCountService;
    private static MostProfitableRouteServiceMock mostProfitableRouteService;

    @BeforeAll
    public static void init() {
        storageService = new StorageServiceMock();
        passengersCountService = new PassengersCountServiceMock();
        mostProfitableRouteService = new MostProfitableRouteServiceMock();
        statsService = new StatsServiceImpl(
                storageService,
                passengersCountService,
                mostProfitableRouteService
        );
    }

    @AfterEach
    public void resetMocks() {
        storageService.reset();
        passengersCountService.reset();
        mostProfitableRouteService.reset();
    }

    @Test
    public void testCollectStats() {
        // given
        List<Route> expectedRoutes = List.of(
                new Route(1, 30.5),
                new Route(2, 33.0),
                new Route(3, 55.0),
                new Route(4, 30.5),
                new Route(5, 55.0),
                new Route(6, 55.0),
                new Route(7, 30.5),
                new Route(8, 55.0),
                new Route(9, 55.0),
                new Route(10, 30.5)
        );
        List<Double> expectedProfits = List.of(
                4148.0,
                5049.0,
                9350.0,
                5185.0,
                9350.0,
                9350.0,
                5185.0,
                8415.0,
                7480.0,
                3629.5
        );
        Set<Driver> expectedDrivers = Set.of(
                new Driver("Адеев Владимир Станиславович", 40),
                new Driver("Сергеев Сергей Сергеевич", 42),
                new Driver("Кириллов Кирилл Кириллович", 36),
                new Driver("Щербаков Виталий Геннадиевич", 33),
                new Driver("Кириллов Кирилл Кириллович", 41),
                new Driver("Михайлов Михаил Михайлович", 34),
                new Driver("Пономарев Максим Сергеевич", 46),
                new Driver("Сергеев Александр Петрович", 27),
                new Driver("Петров Петр Петрович", 51),
                new Driver("Увалов Дмитрий Германович", 55),
                new Driver("Добров Кирилл Сергеевич", 24),
                new Driver("Хитров Евгений Борисович", 35),
                new Driver("Борисов Борис Борисович", 20)
        );

        storageService.stubbedDriverSchedulesResult = DriverScheduleGenerator.generateScheduleList(13);
        storageService.stubbedRoutesResult = new ArrayList<>();
        storageService.stubbedProfitsResult = new ArrayList<>();
        storageService.stubbedDriversResult = new HashSet<>();
        passengersCountService.stubbedGetPassengersCountResult = 17;

        // when
        statsService.collectStats();

        // then
        assertEquals(0, mostProfitableRouteService.invokedMostProfitableRouteCount);
        assertEquals(91, passengersCountService.invokedGetPassengersCount);
        assertEquals(1, storageService.invokedDriverSchedulesCount);
        assertEquals(202, storageService.invokedRoutesCount);
        assertEquals(192, storageService.invokedProfitsCount);
        assertEquals(0, storageService.invokedDriversCount);
        assertEquals(expectedRoutes, storageService.routes());
        assertEquals(expectedProfits, storageService.profits());
        assertEquals(expectedDrivers, storageService.drivers());
    }

    @Test
    void testCollectStatsWhenSchedulesEmpty() {
        // given
        storageService.stubbedDriverSchedulesResult = DriverScheduleGenerator.generateScheduleList(0);
        storageService.stubbedRoutesResult = List.of();
        storageService.stubbedProfitsResult = List.of();
        storageService.stubbedDriversResult = Set.of();

        // when
        statsService.collectStats();

        // then
        assertEquals(0, mostProfitableRouteService.invokedMostProfitableRouteCount);
        assertEquals(0, passengersCountService.invokedGetPassengersCount);
        assertEquals(1, storageService.invokedDriverSchedulesCount);
        assertEquals(0, storageService.invokedRoutesCount);
        assertEquals(0, storageService.invokedProfitsCount);
        assertEquals(0, storageService.invokedDriversCount);
        assertTrue(storageService.routes().isEmpty());
        assertTrue(storageService.profits().isEmpty());
        assertTrue(storageService.drivers().isEmpty());
    }
}