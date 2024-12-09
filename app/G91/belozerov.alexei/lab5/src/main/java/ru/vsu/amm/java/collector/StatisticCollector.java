package ru.vsu.amm.java.collector;

import java.util.List;

public record StatisticCollector(List<String> packageStatistic,
                                 List<String> classStatistic,
                                 List<String> methodStatistic,
                                 List<String> fieldStatistic) {

}
