package ru.vsu.amm.java.impl;

import ru.vsu.amm.java.Annotation.Benchmarked;
import ru.vsu.amm.java.service.Service;

public class ServiceImpl implements Service {

    private static int seconds;

    @Benchmarked
    @Override
    public void do1() {
        seconds = 100;
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Benchmarked
    @Override
    public void do2() {
        seconds = 200;
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void do3() {
        seconds = 150;
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}