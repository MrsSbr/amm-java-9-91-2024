package ru.vsu.amm.java;

import java.util.Arrays;

public class Main {
    private static final int THREADS_COUNT = 5;
    private static final int MILLISECONDS_DELAY = 5000;

    public static void main(String[] args) {
        try {
            var service = new ThreadsService(THREADS_COUNT);
            service.start();
            Thread.sleep(MILLISECONDS_DELAY);
            service.join();

            long controlSum = service.getControlSum();
            long threadsSum = Arrays.stream(service.getThreadSums()).sum();

            System.out.println("Control sum: " + controlSum);
            System.out.println("Threads sum: " + threadsSum);
            for (int i = 0; i < THREADS_COUNT; ++i) {
                System.out.println("Thread " + i + " sum: " + service.getThreadSums()[i]);
            }
        } catch (InterruptedException | RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}