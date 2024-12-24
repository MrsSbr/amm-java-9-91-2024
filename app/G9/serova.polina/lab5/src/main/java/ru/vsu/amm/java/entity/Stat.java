package ru.vsu.amm.java.entity;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class Stat {
    private final Map<Method, Long> stats;

    public Stat() {
        this.stats = new HashMap<>();
    }

    public void insertStat(Method method, Long timeMs) {
        this.stats.merge(method, timeMs, (oldVal, newVal) -> oldVal/2 + newVal/2);
    }

    public void print(PrintStream stream) {
        stats.forEach(
                (method, timeMs) -> {
                    stream.println("Метод: " + method.getName());
                    stream.println("Время выполнения в мс: " + timeMs);
                }
        );
    }
}
