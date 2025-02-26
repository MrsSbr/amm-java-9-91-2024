package ru.vsu.amm.java;

import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.LongAccumulator;

public class Main {
    private final static int COUNT = 4;

    public static void main(String[] args) throws InterruptedException {
        LongAccumulator globalSum = new LongAccumulator(Long::sum, 0);
        ConcurrentLinkedQueue<String> inputQueue = new ConcurrentLinkedQueue<>();
        AtomicBoolean running = new AtomicBoolean(true);
        Thread[] threads = new Thread[COUNT];
        Worker[] workers = new Worker[COUNT];

        for (int i = 0; i < threads.length; i++) {
            workers[i] = new Worker(globalSum, inputQueue, running);
            threads[i] = new Thread(workers[i]);
            threads[i].start();
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if ("end".equalsIgnoreCase(input)) {
                inputQueue.add("end");
                break;
            } else {
                inputQueue.add(input);
            }
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Global sum: " + globalSum.get());
        long localSumsTotal = 0;
        for (int i = 0; i < COUNT; i++) {
            long localSum = workers[i].getLocalSum();
            localSumsTotal += localSum;
            System.out.println("Thread " + (i + 1) + " Local Sum: " + localSum);
        }

        System.out.println("Sum of all local sums: " + localSumsTotal);
        if (globalSum.get() == localSumsTotal) {
            System.out.println("Sums match!");
        } else {
            System.out.println("Sums do not match.");
        }
    }
}