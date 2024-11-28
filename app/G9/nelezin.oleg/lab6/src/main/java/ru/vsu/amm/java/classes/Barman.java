package ru.vsu.amm.java.classes;

import lombok.AllArgsConstructor;
import ru.vsu.amm.java.enums.Component;

import java.util.logging.Logger;

@AllArgsConstructor
public class Barman extends Thread {

    private static final Logger log;

    static {
        log = Logger.getLogger(Barman.class.getName());
    }

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
                log.info("error");
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
