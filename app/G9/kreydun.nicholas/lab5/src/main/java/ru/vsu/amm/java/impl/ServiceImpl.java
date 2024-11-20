package ru.vsu.amm.java.impl;

import ru.vsu.amm.java.Annotation.Benchmarked;
import ru.vsu.amm.java.service.Service;

public class ServiceImpl implements Service {

    @Benchmarked
    @Override
    public void do1() {
        int SECONDS = 100;
        try {
            Thread.sleep(SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Benchmarked
    @Override
    public void do2() {
        int SECONDS = 200;
        try {
            Thread.sleep(SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void do3() {
        int SECONDS = 50;
        try {
            Thread.sleep(SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}