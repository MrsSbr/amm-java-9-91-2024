package ru.vsu.amm.java.impl;

import ru.vsu.amm.java.Annotation.Benchmarked;
import ru.vsu.amm.java.service.Service;

public class ServiceImpl implements Service {

    int SECONDS;

    @Benchmarked
    @Override
    public void do1() {
        SECONDS = 100;
        try {
            Thread.sleep(SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Benchmarked
    @Override
    public void do2() {
        SECONDS = 200;
        try {
            Thread.sleep(SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void do3() {
        SECONDS = 150;
        try {
            Thread.sleep(SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}