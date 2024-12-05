package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.enums.Part;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PartProducer extends Thread {
    private final BlockingQueue<Part> queue;
    private final Part partType;
    private static final Logger logger = Logger.getLogger(PartProducer.class.getName());

    public PartProducer(BlockingQueue<Part> queue, Part partType) {
        this.queue = queue;
        this.partType = partType;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                Thread.sleep(partType.getProductionTime() * 1000); // Simulate production time
                queue.put(partType);
                logger.log(Level.INFO, "Произведена деталь: {0}", partType);
            }
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Производитель деталей прерван", e);
        }
    }
}

