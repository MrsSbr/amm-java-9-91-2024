package ru.vsu.amm.java.service;

import ru.vsu.amm.java.Annotation.Benchmarked;

// Интерфейс с методами, которые будем отслеживать
public interface Service {

    @Benchmarked
    void do1();

    @Benchmarked
    void do2();

    @Benchmarked
    void do3();
}