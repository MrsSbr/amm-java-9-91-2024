package ru.vsu.amm.java;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.LongAccumulator;

public class Worker implements Runnable {
    private final LongAccumulator globalSum;
    private final ConcurrentLinkedQueue<String> inputQueue;
    private final AtomicBoolean running;
    private long localSum = 0;

    public Worker(LongAccumulator globalSum, ConcurrentLinkedQueue<String> inputQueue, AtomicBoolean running) {
        this.globalSum = globalSum;
        this.inputQueue = inputQueue;
        this.running = running;
    }

    public long getLocalSum() {
        return localSum;
    }

    @Override
    public void run() {
        try {
            while (running.get()) {
                String input = inputQueue.poll();
                if (input == null) {
                    continue;
                }
                if ("end".equalsIgnoreCase(input)) {
                    running.set(false);
                    break;
                }
                try {
                    long number = Long.parseLong(input);
                    globalSum.accumulate(number);
                    localSum += number;
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing number: " + input + ", write end or number");
                }
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
}