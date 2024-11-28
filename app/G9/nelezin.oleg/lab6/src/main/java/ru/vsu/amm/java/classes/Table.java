package ru.vsu.amm.java.classes;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class Table {

    private static final Logger log;

    static {
        log = Logger.getLogger(Table.class.getName());
    }

    private final BlockingQueue<String[]> componentQueue = new ArrayBlockingQueue<>(1);

    public void placeComponents(String component1, String component2) {
        try {
            componentQueue.put(new String[]{component1, component2});
            System.out.println("Бармен положил " + component1 + ", " + component2);
        } catch (InterruptedException e) {
            log.info("error");
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public void takeComponents(String needComponent) {
        try {
            while (true) {
                String[] components = componentQueue.take();
                if (!components[0].equals(needComponent) && !components[1].equals(needComponent)) {
                    System.out.println("Курильщик с " + needComponent + " забирает компоненты");
                    return;
                }
                componentQueue.put(components);
            }
        } catch (InterruptedException e) {
            log.info("error");
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
