package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.enums.Part;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScrewAssembler extends Thread {
    private final BlockingQueue<Part> part1Queue;
    private final BlockingQueue<Part> moduleQueue;
    private static final Logger logger = Logger.getLogger(ScrewAssembler.class.getName());
    private static final int milliseconds = 1000;


    public ScrewAssembler(BlockingQueue<Part> part1Queue, BlockingQueue<Part> moduleQueue) {
        this.part1Queue = part1Queue;
        this.moduleQueue = moduleQueue;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                Part part1 = part1Queue.take();
                boolean assembleScrew = false;

                if (part1 == Part.PART1) {
                    Part module = moduleQueue.take();
                    if (module == Part.MODULE) {
                        assembleScrew = true;
                    } else {
                        moduleQueue.put(module);
                    }
                } else {
                    part1Queue.put(part1);
                }

                if (assembleScrew) {
                    Thread.sleep(milliseconds);
                    logger.log(Level.INFO, "Собран винт");
                }
            }
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Сборка винтов прервана", e);
        }
    }
}
