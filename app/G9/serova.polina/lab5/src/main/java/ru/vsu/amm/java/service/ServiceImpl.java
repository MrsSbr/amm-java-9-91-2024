package ru.vsu.amm.java.service;


public class ServiceImpl implements Service {

    @Override
    public void do1() throws InterruptedException {
        Thread.sleep(1000);
    }

    @Override
    public void do2() throws InterruptedException {
        Thread.sleep(2000);
    }

    @Override
    public void do3() throws InterruptedException {
        Thread.sleep(3000);
    }
}
