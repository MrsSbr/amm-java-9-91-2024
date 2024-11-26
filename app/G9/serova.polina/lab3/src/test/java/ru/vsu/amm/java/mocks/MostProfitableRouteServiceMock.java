package ru.vsu.amm.java.mocks;

import ru.vsu.amm.java.entity.Route;
import ru.vsu.amm.java.service.MostProfitableRouteService;

import java.util.List;


public class MostProfitableRouteServiceMock implements MostProfitableRouteService {

    public Route stubbedMostProfitableRouteResult;
    public int invokedMostProfitableRouteCount;

    public MostProfitableRouteServiceMock() {
        this.stubbedMostProfitableRouteResult = null;
        this.invokedMostProfitableRouteCount = 0;
    }

    public void reset() {
        this.stubbedMostProfitableRouteResult = null;
        this.invokedMostProfitableRouteCount = 0;
    }

    @Override
    public Route mostProfitableRoute(List<Route> routes, List<Double> profits) {
        this.invokedMostProfitableRouteCount += 1;
        return this.stubbedMostProfitableRouteResult;
    }
}
