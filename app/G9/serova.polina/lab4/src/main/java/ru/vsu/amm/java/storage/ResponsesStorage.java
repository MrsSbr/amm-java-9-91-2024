package ru.vsu.amm.java.storage;


import ru.vsu.amm.java.entity.City;
import ru.vsu.amm.java.entity.Response;

import java.util.Map;

public interface ResponsesStorage {

    void insertResponse(City city, Integer respondentsCount, Response response);
    Map<City, Map<Response, Integer>> getResponses();
}
