package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAccumulator;

public class ThreadsService {
    private final LongAccumulator controlSum;
    private final List<Thread> threads;
    private final long[] threadSums;
    private final Semaphore semaphore;
    private boolean isStopped;
    private final Scanner console;

    public ThreadsService(int threadsCount) {
        controlSum = new LongAccumulator(Long::sum, 0);
        threads = new ArrayList<>();
        threadSums = new long[threadsCount];
        semaphore = new Semaphore(1);
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
                try {
                    if (semaphore.tryAcquire(1, 0, TimeUnit.MILLISECONDS)) {
                        try {
                            long value = 0;
                            System.out.print(id + " > ");
                            String line = console.nextLine();
                            value = Long.parseLong(line);
                            semaphore.release();
                            controlSum.accumulate(value);
                            threadSums[id] += value;
                        } catch (NumberFormatException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
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

    public long getControlSum() {
        return controlSum.get();
    }

    public long[] getThreadSums() {
        return threadSums;
    }
}
