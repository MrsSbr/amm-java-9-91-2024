package ru.vsu.amm.java.models;

import ru.vsu.amm.java.interfaces.Subscriber;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class MessageBroker {
    private static final Logger logger = Logger.getLogger(MessageBroker.class.getName());
    private final Map<String, List<Subscriber>> topicSubscribers = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public void subscribe(String topic, Subscriber subscriber) {
        topicSubscribers.computeIfAbsent(topic, _ ->
                new CopyOnWriteArrayList<>()).add(subscriber);
    }

    public void unsubscribe(String topic, Subscriber subscriber) {
        List<Subscriber> subscribers = topicSubscribers.get(topic);
        if (subscribers != null) {
            subscribers.remove(subscriber);
        }
    }

    public void publish(String topic, String message) {
        List<Subscriber> subscribers = topicSubscribers.get(topic);
        if (subscribers != null) {
            for (Subscriber subscriber : subscribers) {
                executor.submit(() -> {
                    try {
                        subscriber.receive(message);
                    } catch (Exception e) {
                        logger.warning(e.getMessage());
                    }
                });

            }
        }
    }

    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            logger.severe(e.getMessage());
            executor.shutdownNow();

        }
    }
}
