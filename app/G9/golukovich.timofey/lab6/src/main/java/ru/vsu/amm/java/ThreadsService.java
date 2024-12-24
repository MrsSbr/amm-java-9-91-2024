package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadsService {
    private final AtomicInteger controlSum;
    private final List<Thread> threads;
    private final int[] threadSums;
    private final Object locker;
    private boolean isStopped;
    private final Scanner console;

    public ThreadsService(int threadsCount) {
        controlSum = new AtomicInteger();
        controlSum.set(0);
        threads = new ArrayList<>();
        threadSums = new int[threadsCount];
        locker = new Object();
        isStopped = false;
        console = new Scanner(System.in);

        for (int i = 0; i < threadsCount; ++i) {
            var thread = getThread(i);
            threads.add(thread);
        }
    }

    private Thread getThread(int id) {
        return new Thread(() -> {
            while (!isStopped) {
                int value = 0;
                synchronized (locker) {
                    System.out.print("> ");
                    String line = console.nextLine();
                    try {
                        value = Integer.parseInt(line);
                    } catch (NumberFormatException e) {
                        System.out.println(e.getMessage());
                    }
                }
                controlSum.addAndGet(value);
                threadSums[id] += value;
            }
        });
    }

    public void start() {
        for (var thread : threads) {
            thread.start();
        }
    }

    public void join() throws InterruptedException {
        isStopped = true;
        for (var thread : threads) {
            thread.join();
        }
        console.close();
    }

    public int getControlSum() {
        return controlSum.get();
    }

    public int[] getThreadSums() {
        return threadSums;
    }
}
