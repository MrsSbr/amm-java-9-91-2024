package ru.vsu.amm.java;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private final static int COUNT = 4;

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger globalSum = new AtomicInteger(0);
        AtomicBoolean running = new AtomicBoolean(true);
        Thread[] threads = new Thread[COUNT];
        Worker[] workers = new Worker[COUNT];

        for (int i = 0; i < threads.length; i++) {
            workers[i] = new Worker(globalSum, running);
            threads[i] = new Thread(workers[i]);
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Global sum: " + globalSum.get());
        for (int i = 0; i < COUNT; i++) {
            System.out.println("Thread " + threads[i].getId() + " Local Sum: " + workers[i].getLocalSum());
        }
    }
}