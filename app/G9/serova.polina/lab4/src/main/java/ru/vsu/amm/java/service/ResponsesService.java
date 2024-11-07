package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.City;
import ru.vsu.amm.java.entity.Response;
import ru.vsu.amm.java.storage.ResponsesStorage;

import java.util.Set;


public interface ResponsesService {

    Response getMostPopularResponseForCities(String startsWith, ResponsesStorage responsesStorage);
    City getMostVariousResponseCity(ResponsesStorage responsesStorage);
    Set<City> getCitiesWithoutPopularMoscowResponse(ResponsesStorage responsesStorage);
}
