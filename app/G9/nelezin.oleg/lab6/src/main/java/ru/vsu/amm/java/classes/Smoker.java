package ru.vsu.amm.java.classes;

import lombok.AllArgsConstructor;

import java.util.logging.Logger;

@AllArgsConstructor
public class Smoker extends Thread {

    private static final Logger log;

    static {
        log = Logger.getLogger(Smoker.class.getName());
    }

    private final static int SMOKING_TIME = 1500;

    private String name;

    private String component;

    private Table table;

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                table.takeComponents(component);
                System.out.println("Курильщик " + name + " курит сигарету");
                Thread.sleep(SMOKING_TIME);
            }
        } catch (InterruptedException e) {
            log.info("error");
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
