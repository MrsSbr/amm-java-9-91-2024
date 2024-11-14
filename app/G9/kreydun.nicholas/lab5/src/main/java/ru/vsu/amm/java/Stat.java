package ru.vsu.amm.java;

import java.lang.reflect.Method;
import java.util.Map;

public class Stat {
    private final Map<Method, Long> methodTimes;

    public Stat(Map<Method, Long> methodTimes) {
        this.methodTimes = methodTimes;
    }

    public void print(Appendable output) {
        methodTimes.forEach((method, time) -> {
            try {
                output.append(method.getName()).append(": ").append(time).append(" ns\n");
            } catch (Exception e) {
                // Handle exception, if needed
            }
        });
    }
}