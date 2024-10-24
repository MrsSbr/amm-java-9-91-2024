package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.Route;
import java.util.List;


public final class MostProfitableRouteServiceImpl implements MostProfitableRouteService {

    @Override
    public Route mostProfitableRoute(List<Route> routes, List<Double> profits) {
        return routes.get(profits.indexOf(
                profits.stream()
                        .max(Double::compareTo)
                        .orElseThrow(NullPointerException::new) // .orElseThrow
                )
        );
    }
}
