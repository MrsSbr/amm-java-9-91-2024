package ru.vsu.amm.java.collector;

import java.util.ArrayList;
import java.util.List;

public class StatisticCollectorBuilder {
    private final List<String> packageStatistic;
    private final List<String> classStatistic;
    private final List<String> methodStatistic;
    private final List<String> fieldStatistic;

    public StatisticCollectorBuilder() {
        packageStatistic = new ArrayList<>();
        classStatistic = new ArrayList<>();
        methodStatistic = new ArrayList<>();
        fieldStatistic = new ArrayList<>();
    }

    public StatisticCollectorBuilder addPackageStatistic(List<String> packageStatistic) {
        this.packageStatistic.addAll(packageStatistic);
        return this;
    }

    public StatisticCollectorBuilder addClassStatistic(List<String> classStatistic) {
        this.classStatistic.addAll(classStatistic);
        return this;
    }

    public StatisticCollectorBuilder addMethodStatistic(List<String> methodStatistic) {
        this.methodStatistic.addAll(methodStatistic);
        return this;
    }

    public StatisticCollectorBuilder addFieldStatistic(List<String> fieldStatistic) {
        this.fieldStatistic.addAll(fieldStatistic);
        return this;
    }

    public StatisticCollector build() {
        return new StatisticCollector(packageStatistic, classStatistic, methodStatistic, fieldStatistic);
    }
}
