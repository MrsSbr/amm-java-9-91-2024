package ru.vsu.amm.java;

// Реализовать message broker с pull-стратегией и гарантией доставки at least once

import ru.vsu.amm.java.messageBroker.Consumer;
import ru.vsu.amm.java.messageBroker.MessageBroker;
import ru.vsu.amm.java.messageBroker.Producer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final int THREAD_NUMBER = 4;

    public static void main(String[] args) {
        MessageBroker broker = new MessageBroker();
        Producer producer1 = new Producer(broker, 1);
        Producer producer2 = new Producer(broker, 2);
        Consumer consumer1 = new Consumer(broker, 1);
        Consumer consumer2 = new Consumer(broker, 2);
        try (ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUMBER)) {
            executor.execute(producer1);
            Thread.sleep(100);
            executor.execute(producer2);
            Thread.sleep(100);
            executor.execute(consumer1);
            Thread.sleep(100);
            executor.execute(consumer2);
            Thread.sleep(10_000);
            executor.shutdown();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}