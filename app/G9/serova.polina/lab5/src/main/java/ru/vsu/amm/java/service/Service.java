package ru.vsu.amm.java.service;

import ru.vsu.amm.java.annotation.Benchmarked;


public interface Service {

    @Benchmarked
    void do1() throws InterruptedException;

    void do2() throws InterruptedException;

    @Benchmarked
    void do3() throws InterruptedException;
}
