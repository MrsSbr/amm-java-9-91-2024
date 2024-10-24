package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.City;
import ru.vsu.amm.java.entity.Response;
import java.util.Set;


public interface ResponsesService {

    void readResponse(City city, int respondentsCount, Response response);
    Response getMostPopularResponseForCities(String startsWith);
    City getMostVariousResponseCity();
    Set<City> getCitiesWithoutPopularMoscowResponse();
}
