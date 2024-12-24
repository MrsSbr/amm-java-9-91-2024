package ru.vsu.amm.java;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            var service = new ThreadsService(5);
            service.start();
            Thread.sleep(5000);
            service.join();

            int controlSum = service.getControlSum();
            int threadsSum = Arrays.stream(service.getThreadSums()).sum();

            System.out.print("Control sum: " + controlSum);
            System.out.print("Threads sum: " + threadsSum);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}