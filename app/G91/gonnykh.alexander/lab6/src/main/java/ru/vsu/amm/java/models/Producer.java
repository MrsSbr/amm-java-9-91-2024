package ru.vsu.amm.java.models;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {
    private static final AtomicInteger idGenerator = new AtomicInteger(1);
    private final MessageBroker broker;
    private final String topic;
    private final int id;

    public Producer(MessageBroker broker, String topic) {
        this.broker = broker;
        this.topic = topic;
        id = idGenerator.getAndIncrement();
    }

    @Override
    public void run() {
        Random rand = new Random();
        for (int i = 1; i <= 5; i++) {
            String randomMessage = Integer.toString(rand.nextInt());
            String message = "Producer" + id + " published into " + topic + ": " + randomMessage;
            System.out.println(message);

            broker.publish(topic, randomMessage);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
