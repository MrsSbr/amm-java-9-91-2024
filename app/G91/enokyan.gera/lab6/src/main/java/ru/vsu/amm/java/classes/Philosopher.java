package ru.vsu.amm.java.classes;

import java.util.Random;

public class Philosopher extends Thread {
    private static final Random random = new Random();

    private final int id;
    private final Fork leftFork;
    private final Fork rightFork;
    private final Plate plate;

    public Philosopher(int id, Fork leftFork, Fork rightFork, Plate plate) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.plate = plate;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                think();
                eat();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void think() throws InterruptedException {
        System.out.println("Философ" + id + " размышляет");
        Thread.sleep(random.nextLong(1000, 3001));
    }

    private void eat() throws InterruptedException {
        if (leftFork.tryPickUp()) {
            try {
                if (rightFork.tryPickUp()) {
                    try {
                        System.out.println("Философ" + id + " ест спагетти");
                        plate.eatSpaghetti(random.nextLong(1000, 3001));
                    } finally {
                        rightFork.putDown();
                    }
                }
            } finally {
                leftFork.putDown();
            }
        }
    }
}
