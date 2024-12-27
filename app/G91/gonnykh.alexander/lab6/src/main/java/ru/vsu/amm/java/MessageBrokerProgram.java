package ru.vsu.amm.java;

import ru.vsu.amm.java.logger.LoggerConfig;
import ru.vsu.amm.java.models.Consumer;
import ru.vsu.amm.java.models.MessageBroker;
import ru.vsu.amm.java.models.Producer;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class MessageBrokerProgram {
    private static final String TOPIC = "news";
    private static final int THREADS = 4;
    private static final Logger logger = Logger.getLogger(MessageBrokerProgram.class.getName());

    public static void main(String[] args) throws IOException {
        LoggerConfig.configure();

        MessageBroker mb = new MessageBroker();

        Producer producer1 = new Producer(mb, TOPIC);
        Producer producer2 = new Producer(mb, TOPIC);

        Consumer consumer1 = new Consumer(mb, TOPIC);
        Consumer consumer2 = new Consumer(mb, TOPIC);

        try (ExecutorService simulationExecutor = Executors.newFixedThreadPool(THREADS)) {
            simulationExecutor.submit(producer1);
            simulationExecutor.submit(producer2);
            simulationExecutor.submit(consumer1);
            simulationExecutor.submit(consumer2);

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            mb.shutdown();

        } catch (IllegalArgumentException e) {
            logger.severe(e.getMessage());
        }
    }
}