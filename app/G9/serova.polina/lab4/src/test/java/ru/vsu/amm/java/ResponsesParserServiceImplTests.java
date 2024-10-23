package ru.vsu.amm.java;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.City;
import ru.vsu.amm.java.entity.Response;
import ru.vsu.amm.java.service.ResponsesParserServiceImpl;
import ru.vsu.amm.java.service.ResponsesServiceMock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponsesParserServiceImplTests {

    private static ResponsesParserServiceImpl responsesParserService;
    private static ResponsesServiceMock responsesService;

    @BeforeAll
    public static void init() {
        responsesService = new ResponsesServiceMock();
        responsesParserService = new ResponsesParserServiceImpl(responsesService);
    }

    @AfterEach
    public void resetMocks() {
        responsesService.reset();
    }

    @Test
    void testParseFile() {
        // given
        List<ResponsesServiceMock.ReadResponseParameters> expectedReadResponseInvokedParameters = List.of(
                new ResponsesServiceMock.ReadResponseParameters(new City("Москва"), 32, new Response("мяу")),
                new ResponsesServiceMock.ReadResponseParameters(new City("Санкт-Петербург"), 45, new Response("ква")),
                new ResponsesServiceMock.ReadResponseParameters(new City("Новосибирск"), 28, new Response("ррр")),
                new ResponsesServiceMock.ReadResponseParameters(new City("Екатеринбург"), 78, new Response("тяф-тяф")),
                new ResponsesServiceMock.ReadResponseParameters(new City("Анапа"), 50, new Response("тяф-тяф")),
                new ResponsesServiceMock.ReadResponseParameters(new City("Астрахань"), 15, new Response("ква"))

        );

        // when
        responsesParserService.parseFile("test_responses.txt");

        // then
        assertEquals(responsesService.readResponseInvokedParameters, expectedReadResponseInvokedParameters);
        assertEquals(6, responsesService.readResponseInvokedCount);
        assertEquals(0, responsesService.getMostPopularResponseForCitiesInvokedCount);
        assertEquals(0, responsesService.getMostVariousResponseCityInvokedCount);
        assertEquals(0, responsesService.getCitiesWithoutPopularMoscowResponseInvokedCount);
    }

    @Test
    void testParseFileWhenEmpty() {
        // when
        responsesParserService.parseFile("emptyFile.txt");

        // then
        assertEquals(0, responsesService.readResponseInvokedCount);
        assertEquals(0, responsesService.getMostPopularResponseForCitiesInvokedCount);
        assertEquals(0, responsesService.getMostVariousResponseCityInvokedCount);
        assertEquals(0, responsesService.getCitiesWithoutPopularMoscowResponseInvokedCount);
    }

    @Test
    void testParseFileWhenNoFile() {
        // when
        responsesParserService.parseFile("");

        // then
        assertEquals(0, responsesService.readResponseInvokedCount);
        assertEquals(0, responsesService.getMostPopularResponseForCitiesInvokedCount);
        assertEquals(0, responsesService.getMostVariousResponseCityInvokedCount);
        assertEquals(0, responsesService.getCitiesWithoutPopularMoscowResponseInvokedCount);
    }

    @Test
    void testParseFileWhenRestrictedFileType() {
        // when
        responsesParserService.parseFile("test.class");

        // then
        assertEquals(0, responsesService.readResponseInvokedCount);
        assertEquals(0, responsesService.getMostPopularResponseForCitiesInvokedCount);
        assertEquals(0, responsesService.getMostVariousResponseCityInvokedCount);
        assertEquals(0, responsesService.getCitiesWithoutPopularMoscowResponseInvokedCount);
    }

    @Test
    void testParseFileWhenIncorrectData() {
        // when
        responsesParserService.parseFile("incorrect_responses.txt");

        // then
        assertEquals(2, responsesService.readResponseInvokedCount);
        assertEquals(0, responsesService.getMostPopularResponseForCitiesInvokedCount);
        assertEquals(0, responsesService.getMostVariousResponseCityInvokedCount);
        assertEquals(0, responsesService.getCitiesWithoutPopularMoscowResponseInvokedCount);
    }
}
