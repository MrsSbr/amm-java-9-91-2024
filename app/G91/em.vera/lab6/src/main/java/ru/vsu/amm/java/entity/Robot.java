package ru.vsu.amm.java.entity;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Robot {
    private static final Logger logger = Logger.getLogger(Robot.class.getName());
    private static final int STEPS_COUNT = 5;
    private static final int STEPS_DELAY = 2000;

    private final Thread leftLeg;
    private final Thread rightLeg;

    private boolean leftStep;


    public Robot() {

        leftLeg = new Thread(() -> {
            try {
                for (int i = 0; i < STEPS_COUNT; i++) {
                    stepOnLeft();
                    Thread.sleep(STEPS_DELAY);
                }
            } catch (InterruptedException e) {

                logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
        rightLeg = new Thread(() -> {
            try {
                for (int i = 0; i < STEPS_COUNT; i++) {
                    stepOnRight();
                    Thread.sleep(STEPS_DELAY);
                }
            } catch (InterruptedException e) {

                logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
    }

    public void startWalking(boolean startingFromLeft) {
        leftStep = startingFromLeft;

        leftLeg.start();
        rightLeg.start();
    }


    private synchronized void stepOnLeft() throws InterruptedException {
        while (!leftStep) {
            wait();
        }
        System.out.println("Left");
        leftStep = false;
        notifyAll();
    }

    private synchronized void stepOnRight() throws InterruptedException {
        while (leftStep) {
            wait();
        }
        System.out.println("Right");
        leftStep = true;
        notifyAll();
    }

}
