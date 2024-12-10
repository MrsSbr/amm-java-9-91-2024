package ru.vsu.amm.java.service;


public class Barber implements Runnable {

    private Barbershop barbershop;
    private boolean hasToFinish;
    public boolean isWorking;

    public Barber() {
        barbershop = null;
        hasToFinish = false;
        isWorking = false;
    }

    public void startWorkingAt(Barbershop barbershop) {
        this.barbershop = barbershop;
    }

    public void goHome() {
        hasToFinish = true;
    }

    @Override
    public void run() {
        while (!hasToFinish) {
            try {
                barbershop.serveCustomer();
            } catch (InterruptedException ignored) {}
        }
        System.out.println("Барбер ушел домой");
    }
}
