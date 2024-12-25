package ru.vsu.amm.java;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Worker implements Runnable {
    private final AtomicInteger globalSum;
    AtomicBoolean running;
    private int localSum = 0;

    public Worker(AtomicInteger globalSum, AtomicBoolean running) {
        this.globalSum = globalSum;
        this.running = running;
    }

    public int getLocalSum() {
        return localSum;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (running.get()) {
            String input;
            synchronized (System.in) {
                if (!running.get()) {
                    break;
                }
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("end")) {
                    running.set(false);
                    break;
                }
            }

            System.out.println("Thread " + Thread.currentThread().getId() + " read " + input);

            try {
                int number = Integer.parseInt(input);
                globalSum.addAndGet(number);
                localSum += number;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a valid number or 'end' to finish.");
            }
        }
    }
}
