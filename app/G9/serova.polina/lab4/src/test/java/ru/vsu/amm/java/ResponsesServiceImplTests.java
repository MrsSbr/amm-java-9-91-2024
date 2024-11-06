package ru.vsu.amm.java;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.City;
import ru.vsu.amm.java.entity.Response;
import ru.vsu.amm.java.service.ResponsesServiceImpl;
import ru.vsu.amm.java.storage.ResponsesStorageMock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ResponsesServiceImplTests {

    private static ResponsesServiceImpl responsesService;
    private static ResponsesStorageMock responsesStorage;

    @BeforeAll
    public static void init() {
        responsesStorage = new ResponsesStorageMock();
        responsesService = new ResponsesServiceImpl(responsesStorage);
    }

    @AfterEach
    public void resetMocks() {
        responsesStorage.reset();
    }

    @Test
    public void testReadResponse() {
        // given
        City expectedCity = new City("test");
        int expectedRespondersCount = 5;
        Response expectedResponse = new Response("ttt");

        // when
        responsesService.readResponse(expectedCity, expectedRespondersCount, expectedResponse);

        // then

        assertEquals(
                responsesStorage.insertResponseInvokedParameters,
                List.of(new ResponsesStorageMock.InsertResponseParameters(expectedCity, expectedRespondersCount, expectedResponse))
        );
    }

    @Test
    public void testGetMostPopularResponseForCities() {
        // given
        responsesStorage.getResponsesStubbedResult = Map.of(
                new City("Москва"), Map.of(new Response("ква"), 5),
                new City("Анапа"), Map.of(new Response("гав"), 5),
                new City("Анадырь"), Map.of(new Response("ква"), 5),
                new City("Астана"), Map.of(new Response("гав"), 5),
                new City("Ялта"), Map.of(new Response("ква"), 5)
        );
        Response expectedResponse = new Response("гав");

        // when
        Response actualResponse = responsesService.getMostPopularResponseForCities("А");

        // then
        assertEquals(expectedResponse, actualResponse);
        assertEquals(1, responsesStorage.getResponsesInvokedCount);
    }

    @Test
    public void testGetMostPopularResponseForCitiesWhenNoData() {
        // given
        responsesStorage.getResponsesStubbedResult = Map.of();

        // when
        Response actualResponse = responsesService.getMostPopularResponseForCities("А");

        // then
        assertNull(actualResponse);
        assertEquals(1, responsesStorage.getResponsesInvokedCount);
    }

    @Test
    public void testGetMostPopularResponseForCitiesWhenStorageNotInitialized() {
        // when
        Response actualResponse = responsesService.getMostPopularResponseForCities("А");

        // then
        assertNull(actualResponse);
        assertEquals(1, responsesStorage.getResponsesInvokedCount);
    }

    @Test
    public void testGetMostVariousResponseCity() {
        // given
        responsesStorage.getResponsesStubbedResult = new HashMap<>();
        responsesStorage.getResponsesStubbedResult.put(new City("Москва"), Map.of(
                new Response("ква"), 5
        ));
        responsesStorage.getResponsesStubbedResult.put(new City("Ялта"), new HashMap<>(Map.of(
                new Response("ква"), 5
        )));
        responsesStorage.getResponsesStubbedResult.put(new City("Анадырь"), Map.of(
                new Response("фыр"), 5,
                new Response("ква"), 5
        ));
        responsesStorage.getResponsesStubbedResult.put(new City("Анапа"), Map.of(
                new Response("гав"), 5,
                new Response("ква"), 5,
                new Response("ррр"), 5
        ));

        City expectedResponse = new City("Анапа");

        // when
        City actualCity = responsesService.getMostVariousResponseCity();

        // then
        assertEquals(expectedResponse, actualCity);
        assertEquals(1, responsesStorage.getResponsesInvokedCount);
    }

    @Test
    public void testGetMostVariousResponseCityWhenNoData() {
        // given
        responsesStorage.getResponsesStubbedResult = Map.of();

        // when
        City actualCity = responsesService.getMostVariousResponseCity();

        // then
        assertNull(actualCity);
        assertEquals(1, responsesStorage.getResponsesInvokedCount);
    }

    @Test
    public void testGetMostVariousResponseCityWhenStorageNotInitialized() {
        // when
        City actualCity = responsesService.getMostVariousResponseCity();

        // then
        assertNull(actualCity);
        assertEquals(1, responsesStorage.getResponsesInvokedCount);
    }

    @Test
    public void testGetCitiesWithoutPopularMoscowResponse() {
        // given
        responsesStorage.getResponsesStubbedResult = Map.of(
                new City("Москва"), Map.of(new Response("ква"), 5),
                new City("Анапа"), Map.of(new Response("гав"), 5),
                new City("Анадырь"), Map.of(new Response("ква"), 5),
                new City("Ялта"), Map.of(new Response("ква"), 5)
        );
        Set<City> expectedCities = Set.of(new City("Анапа"));

        // when
        Set<City> actualCities = responsesService.getCitiesWithoutPopularMoscowResponse();

        // then
        assertEquals(expectedCities, actualCities);
        assertEquals(2, responsesStorage.getResponsesInvokedCount);
    }

    @Test
    public void testGetCitiesWithoutPopularMoscowResponseWhenNoMoscowResponseInOther() {
        // given
        responsesStorage.getResponsesStubbedResult = Map.of(
                new City("Москва"), Map.of(new Response("ква"), 5),
                new City("Анапа"), Map.of(new Response("ква"), 5),
                new City("Анадырь"), Map.of(new Response("ква"), 5),
                new City("Ялта"), Map.of(new Response("ква"), 5)
        );

        // when
        Set<City> actualCities = responsesService.getCitiesWithoutPopularMoscowResponse();

        // then
        assertNotNull(actualCities);
        assertTrue(actualCities.isEmpty());
        assertEquals(2, responsesStorage.getResponsesInvokedCount);
    }

    @Test
    public void testGetCitiesWithoutPopularMoscowResponseWhenNoData() {
        // given
        responsesStorage.getResponsesStubbedResult = Map.of();

        // when
        Set<City> actualCities = responsesService.getCitiesWithoutPopularMoscowResponse();

        // then
        assertNull(actualCities);
        assertEquals(1, responsesStorage.getResponsesInvokedCount);
    }

    @Test
    public void testGetCitiesWithoutPopularMoscowResponseWhenStorageNotInitialized() {
        // when
        Set<City> actualCities = responsesService.getCitiesWithoutPopularMoscowResponse();

        // then
        assertNull(actualCities);
        assertEquals(1, responsesStorage.getResponsesInvokedCount);
    }
}
