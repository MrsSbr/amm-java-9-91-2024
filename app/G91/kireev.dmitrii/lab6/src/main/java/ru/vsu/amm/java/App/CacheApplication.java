package ru.vsu.amm.java.App;

import ru.vsu.amm.java.Thread.Scheduler;

public class CacheApplication {

    public static void main(String[] args) {
        int threadsCount = 6;
        int capacity = 100;
        int sleepTime = 10000;
        Scheduler scheduler = new Scheduler(threadsCount, capacity);
        scheduler.start();

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        scheduler.stop();
    }
}
