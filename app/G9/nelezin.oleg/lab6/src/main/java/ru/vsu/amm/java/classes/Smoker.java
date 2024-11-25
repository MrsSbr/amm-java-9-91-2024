package ru.vsu.amm.java.classes;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Smoker extends Thread {

    private final static int SMOKING_TIME = 1500;

    private String name;

    private String component;

    private Table table;


    @Override
    public void run() {
        while (true) {
                table.takeComponents(component);
                System.out.println("Курильщик " + name + " курит сигарету");
                try {
                    Thread.sleep(SMOKING_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
}
