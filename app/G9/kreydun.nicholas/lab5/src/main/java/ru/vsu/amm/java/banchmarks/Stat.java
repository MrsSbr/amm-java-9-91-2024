package ru.vsu.amm.java.banchmarks;

import java.util.Map;

public class Stat {
    private final Map<String, Long> executionTimes;

    public Stat(Map<String, Long> executionTimes) {
        this.executionTimes = executionTimes;
    }

    public void print(java.io.PrintStream out) {
        executionTimes.forEach((methodName, time) ->
                out.println(methodName + "Took " + time + " nanoseconds")
        );
    }

    @Override
    public String toString() {
        return executionTimes.toString();
    }
}