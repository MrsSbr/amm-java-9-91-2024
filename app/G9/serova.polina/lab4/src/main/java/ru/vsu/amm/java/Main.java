package ru.vsu.amm.java;


import ru.vsu.amm.java.service.ResponsesParserService;
import ru.vsu.amm.java.service.ResponsesParserServiceImpl;
import ru.vsu.amm.java.service.ResponsesService;
import ru.vsu.amm.java.service.ResponsesServiceImpl;
import ru.vsu.amm.java.storage.ResponsesStorage;
import ru.vsu.amm.java.storage.ResponsesStorageImpl;

public class Main {
    public static void main(String[] args) {
        ResponsesStorage responsesStorage = new ResponsesStorageImpl();
        ResponsesService responsesService = new ResponsesServiceImpl(responsesStorage);
        ResponsesParserService responsesParserService = new ResponsesParserServiceImpl(responsesService);
        responsesParserService.parseFile("responses.txt");
        responsesService.getCitiesWithoutPopularMoscowResponse();
        responsesService.getMostVariousResponseCity();
        responsesService.getMostPopularResponseForCities("А");
    }
}