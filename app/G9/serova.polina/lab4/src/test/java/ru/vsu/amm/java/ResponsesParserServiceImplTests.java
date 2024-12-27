package ru.vsu.amm.java;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.City;
import ru.vsu.amm.java.entity.Response;
import ru.vsu.amm.java.service.ResponsesParserServiceImpl;
import ru.vsu.amm.java.storage.ResponsesStorageMock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponsesParserServiceImplTests {

    private static ResponsesParserServiceImpl responsesParserService;
    private static ResponsesStorageMock responsesStorage;

    @BeforeAll
    public static void init() {
        responsesStorage = new ResponsesStorageMock();
        responsesParserService = new ResponsesParserServiceImpl();
    }

    @AfterEach
    public void resetMocks() {
        responsesStorage.reset();
    }

    @Test
    void testParseFile() {
        // given
        List<ResponsesStorageMock.InsertResponseParameters> expectedReadResponseInvokedParameters = List.of(
                new ResponsesStorageMock.InsertResponseParameters(new City("Москва"), 32, new Response("мяу")),
                new ResponsesStorageMock.InsertResponseParameters(new City("Санкт-Петербург"), 45, new Response("ква")),
                new ResponsesStorageMock.InsertResponseParameters(new City("Новосибирск"), 28, new Response("ррр")),
                new ResponsesStorageMock.InsertResponseParameters(new City("Екатеринбург"), 78, new Response("тяф-тяф")),
                new ResponsesStorageMock.InsertResponseParameters(new City("Анапа"), 50, new Response("тяф-тяф")),
                new ResponsesStorageMock.InsertResponseParameters(new City("Астрахань"), 15, new Response("ква"))

        );

        // when
        responsesParserService.parseFile("/test_responses.txt", responsesStorage);

        // then
        assertEquals(responsesStorage.insertResponseInvokedParameters, expectedReadResponseInvokedParameters);
        assertEquals(6, responsesStorage.insertResponseInvokedCount);
        assertEquals(0, responsesStorage.getResponsesInvokedCount);
    }

    @Test
    void testParseFileWhenEmpty() {
        // when
        responsesParserService.parseFile("/emptyFile.txt", responsesStorage);

        // then
        assertEquals(0, responsesStorage.insertResponseInvokedCount);
        assertEquals(0, responsesStorage.getResponsesInvokedCount);
    }

    @Test
    void testParseFileWhenNoFile() {
        // when
        responsesParserService.parseFile("", responsesStorage);

        // then
        assertEquals(0, responsesStorage.insertResponseInvokedCount);
        assertEquals(0, responsesStorage.getResponsesInvokedCount);
    }

    @Test
    void testParseFileWhenRestrictedFileType() {
        // when
        responsesParserService.parseFile("test.class", responsesStorage);

        // then
        assertEquals(0, responsesStorage.insertResponseInvokedCount);
        assertEquals(0, responsesStorage.getResponsesInvokedCount);
    }

    @Test
    void testParseFileWhenIncorrectData() {
        // when
        responsesParserService.parseFile("/incorrect_responses.txt", responsesStorage);

        // then
        assertEquals(2, responsesStorage.insertResponseInvokedCount);
        assertEquals(0, responsesStorage.getResponsesInvokedCount);
    }
}
