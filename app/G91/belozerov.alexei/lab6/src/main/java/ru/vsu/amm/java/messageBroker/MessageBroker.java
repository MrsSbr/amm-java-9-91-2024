package ru.vsu.amm.java.messageBroker;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

public class MessageBroker {

    private final Queue<Message> messageQueue = new ConcurrentLinkedQueue<>();
    private final Queue<Message> savedMessages = new ConcurrentLinkedQueue<>();
    private final ConcurrentHashMap<Long, List<Message>> consumerIds = new ConcurrentHashMap<>();
    private final AtomicLong messageIdGenerator = new AtomicLong();

    public void subscribe(long id) {
        consumerIds.put(id, new CopyOnWriteArrayList<>());
    }

    public void unsubscribe(long id) {
        consumerIds.remove(id);
    }

    public long produce(String message) {
        Message resMessage = null;
        if (!isMessageLost()) {
            resMessage = new Message(messageIdGenerator.incrementAndGet(), message);
            messageQueue.add(resMessage);
        }
        if (resMessage == null || isMessageLost()) {
            if (resMessage != null) {
                messageIdGenerator.decrementAndGet();
            }
            return -1;
        } else {
            return messageIdGenerator.get();
        }
    }

    public List<Message> consume(long id) {
        if (consumerIds.isEmpty()) {
            savedMessages.clear();
        }
        if (!messageQueue.isEmpty() && savedMessages.isEmpty()) {
            savedMessages.addAll(messageQueue);
            messageQueue.clear();
        }
        if (consumerIds.get(id).isEmpty()) {
            consumerIds.put(id, new CopyOnWriteArrayList<>(savedMessages));
        }
        if (isMessageLost()) {
            return null;
        }
        List<Message> messages = new CopyOnWriteArrayList<>(consumerIds.get(id));
        unsubscribe(id);
        return messages;
    }

    private boolean isMessageLost() {
        return ThreadLocalRandom.current().nextInt(100) < 10;
    }
}

