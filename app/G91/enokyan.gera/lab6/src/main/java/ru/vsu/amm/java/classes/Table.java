package ru.vsu.amm.java.classes;

public class Table {
    private final Fork[] forks;
    private final Plate[] plates;

    public Table(int numSeats) {
        forks = new Fork[numSeats];
        plates = new Plate[numSeats];
        for (int i = 0; i < numSeats; ++i) {
            forks[i] = new Fork();
            plates[i] = new Plate();
        }
    }

    public Fork getLeftFork(int philosopherId) {
        return forks[philosopherId];
    }

    public Fork getRightFork(int philosopherId) {
        return forks[(philosopherId + 1) % forks.length];
    }

    public Plate getPlate(int philosopherId) {
        return plates[philosopherId];
    }
}
