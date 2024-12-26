package ru.vsu.amm.java.messageBroker;

import java.util.List;
import java.util.stream.Collectors;

public class Consumer implements Runnable {
    private final MessageBroker broker;
    private final long id;

    public Consumer(MessageBroker broker, long id) {
        this.broker = broker;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            broker.subscribe(id);
            List<Message> messages;
            do {
                messages = broker.consume(id);
            } while (messages == null);
            messages.stream()
                    .collect(Collectors.groupingBy(Message::id))
                    .forEach((k, v) ->
                            System.out.println("Consumer " + id + " consumed: " + k + " -> " + v.getFirst().message()));
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
