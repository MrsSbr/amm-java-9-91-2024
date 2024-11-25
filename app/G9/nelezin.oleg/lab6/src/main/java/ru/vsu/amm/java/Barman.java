package ru.vsu.amm.java;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Barman extends Thread {

    private static final Component[] COMPONENTS = {
            Component.PAPER,
            Component.MATCHES,
            Component.TOBACCO
    };

    private Table table;

    @Override
    public void run() {
        while (true) {
            int first = (int) (Math.random() * 3);
            int second = (int) (Math.random() * 3);
            while (first == second) {
                second = (int) (Math.random() * 3);
            }
            table.placeComponents(COMPONENTS[first].getName(), COMPONENTS[second].getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
