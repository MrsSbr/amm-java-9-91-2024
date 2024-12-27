package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.enums.Part;

import java.util.concurrent.BlockingQueue;

public class PartProducer extends Thread {
    private final BlockingQueue<Part> queue;
    private final Part partType;
    private static final int milliseconds = 1000;

    public PartProducer(BlockingQueue<Part> queue, Part partType) {
        this.queue = queue;
        this.partType = partType;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                Thread.sleep(partType.getProductionTime() * milliseconds);
                queue.put(partType);
                System.out.println("Произведена деталь: " + partType.name());// todo вывод в консоль
            }
        } catch (InterruptedException e) {
            System.out.println("Производитель деталей прерван");
        }
    }
}

