package ru.vsu.amm.java;

import ru.vsu.amm.java.classes.Fork;
import ru.vsu.amm.java.classes.Philosopher;
import ru.vsu.amm.java.classes.Plate;
import ru.vsu.amm.java.classes.Table;

import java.util.concurrent.Executors;

public class DiningPhilosophers {
    public static void main(String[] args) {
        final int numSeats = 5;

        var table = new Table(numSeats);

        try (var executorService = Executors.newFixedThreadPool(numSeats)) {
            for (int i = 0; i < numSeats; ++i) {
                Fork leftFork = table.getLeftFork(i);
                Fork rightFork = table.getRightFork(i);
                Plate plate = table.getPlate(i);

                executorService.submit(new Philosopher(i, leftFork, rightFork, plate));
            }
        }
    }
}