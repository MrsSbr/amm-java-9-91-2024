package ru.vsu.amm.java.storage;

import ru.vsu.amm.java.entity.City;
import ru.vsu.amm.java.entity.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ResponsesStorageMock implements ResponsesStorage {

    public Map<City, Map<Response, Integer>> getResponsesStubbedResult;

    public List<InsertResponseParameters> insertResponseInvokedParameters;

    public int insertResponseInvokedCount;
    public int getResponsesInvokedCount;

    public ResponsesStorageMock() {
        this.getResponsesStubbedResult = null;
        this.insertResponseInvokedParameters = new ArrayList<>();
        this.insertResponseInvokedCount = 0;
        this.getResponsesInvokedCount = 0;
    }

    public void reset() {
        this.getResponsesStubbedResult = null;
        this.insertResponseInvokedParameters = new ArrayList<>();
        this.insertResponseInvokedCount = 0;
        this.getResponsesInvokedCount = 0;
    }

    @Override
    public void insertResponse(City city, Integer respondentsCount, Response response) {
        this.insertResponseInvokedCount += 1;
        insertResponseInvokedParameters.add(new InsertResponseParameters(city, respondentsCount, response));
    }

    @Override
    public Map<City, Map<Response, Integer>> getResponses() {
        this.getResponsesInvokedCount += 1;
        return this.getResponsesStubbedResult;
    }

    public record InsertResponseParameters(City city, int respondentsCount, Response response) {}
}
