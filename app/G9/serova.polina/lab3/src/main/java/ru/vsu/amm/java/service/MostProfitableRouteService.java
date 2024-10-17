package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.Route;

import java.util.List;

public interface MostProfitableRouteService {

    Route mostProfitableRoute(List<Route> routes, List<Double> profits);
}
