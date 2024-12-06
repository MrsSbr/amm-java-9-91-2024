package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.enums.Part;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModuleAssembler extends Thread {
    private final BlockingQueue<Part> inputQueue;
    private final BlockingQueue<Part> outputQueue;
    private static final Logger logger = Logger.getLogger(ModuleAssembler.class.getName());
    private static final int milliseconds = 1000;

    public ModuleAssembler(BlockingQueue<Part> inputQueue, BlockingQueue<Part> outputQueue) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                Part part2 = inputQueue.take();
                boolean assembleModule = false;

                if (part2 == Part.PART2) {
                    Part part3 = inputQueue.take();
                    if (part3 == Part.PART3) {
                        assembleModule = true;
                    } else {
                        inputQueue.put(part3);
                        inputQueue.put(part2);
                    }
                } else {
                    inputQueue.put(part2);
                }

                if (assembleModule) {
                    Thread.sleep(milliseconds);
                    outputQueue.put(Part.MODULE);
                    logger.log(Level.INFO, "Собран модуль");
                }
            }
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Сборка модулей прервана", e);
        }
    }
}
