package ru.vsu.amm.java.service;


import ru.vsu.amm.java.entity.City;
import ru.vsu.amm.java.entity.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ResponsesServiceMock implements ResponsesService {

    public Response getMostPopularResponseForCitiesStubbedResult;
    public City getMostVariousResponseCityStubbedResult;
    public Set<City> getCitiesWithoutPopularMoscowResponseStubbedResult;

    public List<ReadResponseParameters> readResponseInvokedParameters;

    public int readResponseInvokedCount;
    public int getMostPopularResponseForCitiesInvokedCount;
    public int getMostVariousResponseCityInvokedCount;
    public int getCitiesWithoutPopularMoscowResponseInvokedCount;

    public ResponsesServiceMock() {
        this.getMostPopularResponseForCitiesStubbedResult = null;
        this.getMostVariousResponseCityStubbedResult = null;
        this.getCitiesWithoutPopularMoscowResponseStubbedResult = null;
        this.readResponseInvokedParameters = new ArrayList<>();
        this.readResponseInvokedCount = 0;
        this.getMostPopularResponseForCitiesInvokedCount = 0;
        this.getMostVariousResponseCityInvokedCount = 0;
        this.getCitiesWithoutPopularMoscowResponseInvokedCount = 0;
    }

    public void reset() {
        this.getMostPopularResponseForCitiesStubbedResult = null;
        this.getMostVariousResponseCityStubbedResult = null;
        this.getCitiesWithoutPopularMoscowResponseStubbedResult = null;
        this.readResponseInvokedParameters = new ArrayList<>();
        this.readResponseInvokedCount = 0;
        this.getMostPopularResponseForCitiesInvokedCount = 0;
        this.getMostVariousResponseCityInvokedCount = 0;
        this.getCitiesWithoutPopularMoscowResponseInvokedCount = 0;
    }

    @Override
    public void readResponse(City city, int respondentsCount, Response response) {
        this.readResponseInvokedCount += 1;
        this.readResponseInvokedParameters.add(new ReadResponseParameters(city, respondentsCount, response));
    }

    @Override
    public Response getMostPopularResponseForCities(String startsWith) {
        this.getMostPopularResponseForCitiesInvokedCount += 1;
        return this.getMostPopularResponseForCitiesStubbedResult;
    }

    @Override
    public City getMostVariousResponseCity() {
        this.getMostVariousResponseCityInvokedCount += 1;
        return this.getMostVariousResponseCityStubbedResult;
    }

    @Override
    public Set<City> getCitiesWithoutPopularMoscowResponse() {
        this.getCitiesWithoutPopularMoscowResponseInvokedCount += 1;
        return this.getCitiesWithoutPopularMoscowResponseStubbedResult;
    }

    public record ReadResponseParameters(City city, int respondentsCount, Response response) {}
}
