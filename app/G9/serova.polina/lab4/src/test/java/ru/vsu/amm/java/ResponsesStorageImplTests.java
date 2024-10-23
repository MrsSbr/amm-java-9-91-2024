package ru.vsu.amm.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.City;
import ru.vsu.amm.java.entity.Response;
import ru.vsu.amm.java.storage.ResponsesStorageImpl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ResponsesStorageImplTests {

    public ResponsesStorageImpl responsesStorage;

    @BeforeEach
    public void resetStorage() {
        responsesStorage = new ResponsesStorageImpl();
    }

    @Test
    public void testInsertResponseWhenNewCity() {
        // given
        City city = new City("test");
        int respondentsCount = 1;
        Response response = new Response("фыр");
        Map<City, Map<Response, Integer>> expectedResponses = Map.of(
                city, Map.of(response, respondentsCount)
        );

        // when
        responsesStorage.insertResponse(city, respondentsCount, response);

        // then
        assertEquals(expectedResponses, responsesStorage.getResponses());
    }

    @Test
    public void testInsertResponseWhenOldCityNewResponse() {
        // given
        City city = new City("test");
        int respondentsCount = 1;
        Response response = new Response("фыр");
        Response newResponse = new Response("мяу");
        Map<City, Map<Response, Integer>> expectedResponses = new HashMap<>();
        expectedResponses.put(city, Map.of(
                response, respondentsCount,
                newResponse, respondentsCount
        ));
        responsesStorage.insertResponse(city, respondentsCount, response);

        // when
        responsesStorage.insertResponse(city, respondentsCount, newResponse);

        // then
        assertEquals(expectedResponses, responsesStorage.getResponses());
    }

    @Test
    public void testInsertResponseWhenOldCityOldResponse() {
        // given
        City city = new City("test");
        int oldRespondentsCount = 17;
        int newRespondentsCount = 22;
        Response response = new Response("фыр");
        Map<City, Map<Response, Integer>> expectedResponses = new HashMap<>();
        expectedResponses.put(city, Map.of(
                response, oldRespondentsCount + newRespondentsCount
        ));
        responsesStorage.insertResponse(city, oldRespondentsCount, response);

        // when
        responsesStorage.insertResponse(city, newRespondentsCount, response);

        // then
        assertEquals(expectedResponses, responsesStorage.getResponses());
    }
}
