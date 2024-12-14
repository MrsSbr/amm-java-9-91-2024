package ru.vsu.amm.java.service;


public class ServiceMock implements Service {

    public boolean shouldThrowException;

    public ServiceMock() {
        shouldThrowException = false;
    }

    public void reset() {
        shouldThrowException = false;
    }

    @Override
    public void do1() throws InterruptedException {
        if (shouldThrowException) {
            throw new InterruptedException();
        }
    }

    @Override
    public void do2() throws InterruptedException {
        if (shouldThrowException) {
            throw new InterruptedException();
        }
    }

    @Override
    public void do3() throws InterruptedException {
        if (shouldThrowException) {
            throw new InterruptedException();
        }
    }
}
