package ru.vsu.amm.java.models;

import ru.vsu.amm.java.interfaces.Subscriber;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements Runnable, Subscriber {
    private static final AtomicInteger idGenerator = new AtomicInteger(1);
    private final MessageBroker broker;
    private final String topic;
    private final int id;

    public Consumer(MessageBroker broker, String topic) {
        this.broker = broker;
        this.topic = topic;
        id = idGenerator.getAndIncrement();
    }

    @Override
    public void run() {
        broker.subscribe(topic, this);
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        broker.unsubscribe(topic, this);
        System.out.println("Consumer" + id + " unsubscribed.");
    }

    @Override
    public void receive(String message) {
        Random random = new Random();
        if (random.nextInt(100) < 20) {
            throw new RuntimeException("Random exception occurred");
        }
        System.out.println("Consumer" + id + " received: " + message);
    }
}
