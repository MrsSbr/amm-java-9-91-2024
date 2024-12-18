package ru.vsu.amm.java.Thread;

import java.util.Random;

public class CacheWriter {

    private final Thread writerThread;
    private final Runnable taskProcessor;
    private boolean running;

    public CacheWriter(Runnable taskProcessor) {
        this.writerThread = new Thread(this::processTask);
        this.taskProcessor = taskProcessor;
        this.running = false;
    }

    private void processTask() {
        Random random = new Random();
        while (running) {
            try {
                Thread.sleep(random.nextInt(500));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            taskProcessor.run();
        }
    }

    public void start() {
        running = true;
        writerThread.start();
    }

    public void stop() {
        running = false;
    }
}
