package ru.vsu.amm.java;

public interface Service {
    @Benchmarked
    void do1();

    @Benchmarked
    void do2();
}
