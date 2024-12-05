package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.enums.Part;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScrewAssembler extends Thread {
    private final BlockingQueue<Part> part1Queue;
    private final BlockingQueue<Part> moduleQueue;
    private static final Logger logger = Logger.getLogger(ScrewAssembler.class.getName());


    public ScrewAssembler(BlockingQueue<Part> part1Queue, BlockingQueue<Part> moduleQueue) {
        this.part1Queue = part1Queue;
        this.moduleQueue = moduleQueue;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                Part part1 = part1Queue.take();
                if (part1 != Part.PART1) {
                    part1Queue.put(part1);
                    continue;
                }
                Part module = moduleQueue.take();
                if (module != Part.MODULE) {
                    moduleQueue.put(module);
                    part1Queue.put(part1);
                    continue;
                }
                Thread.sleep(1000); // Simulate screw assembly time
                logger.log(Level.INFO, "Собран винт");
            }
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Сборка винтов прервана", e);
        }
    }
}
