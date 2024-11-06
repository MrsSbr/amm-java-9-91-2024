package ru.vsu.amm.java.storage;

import ru.vsu.amm.java.entity.City;
import ru.vsu.amm.java.entity.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ResponsesStorageImpl implements ResponsesStorage {

    private final static Logger logger;
    private final Map<City, Map<Response, Integer>> responses;

    static {
        logger = Logger.getLogger(ResponsesStorageImpl.class.getName());
    }

    public ResponsesStorageImpl() {
        this.responses = new HashMap<>();
        logger.log(Level.INFO, "Проинициализировали хранилище ответов");
    }

    @Override
    public void insertResponse(City city, Integer respondentsCount, Response response) {
        this.responses.computeIfAbsent(city, k -> new HashMap<>());
        this.responses.get(city).merge(response, respondentsCount, Integer::sum);

        logger.log(
                Level.INFO,
                "Записали ответ в хранилище: Город: " + city
                        + ", текущее кол-во респондентов: " + this.responses.get(city).get(response)
                        + ", Ответ: " + response
        );
    }

    @Override
    public Map<City, Map<Response, Integer>> getResponses() {
        return this.responses;
    }
}
