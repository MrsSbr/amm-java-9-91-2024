package ru.vsu.amm.java.messageBroker;

import java.util.concurrent.ThreadLocalRandom;

public class Producer implements Runnable {
    private final MessageBroker broker;
    private final long id;

    public Producer(MessageBroker broker, long id) {
        this.broker = broker;
        this.id = id;
    }

    @Override
    public void run() {
        long id;
        String message = "Message " + ThreadLocalRandom.current().nextInt(100);
        do {
            id = broker.produce(message);
        }
        while (id == -1);
        System.out.println("Producer " + this.id + " produced: " + id + " -> " + message);
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

