package ru.vsu.amm.java.App;

import ru.vsu.amm.java.Thread.Scheduler;

public class CacheApplication {

    public static void main(String[] args) {
        int threadsCount = 3;
        int capacity = 100;

        Scheduler scheduler = new Scheduler(threadsCount, capacity);
        scheduler.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        scheduler.stop();
    }
}
