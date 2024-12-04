package ru.vsu.amm.java.service;


public class Barber implements Runnable {

    private Barbershop barbershop;
    public boolean isWorking;

    public Barber() {
        barbershop = null;
        isWorking = false;
    }

    public void startWorkingAt(Barbershop barbershop) {
        this.barbershop = barbershop;
    }

    @Override
    public void run() {
        while (!barbershop.isClosed()) {
            barbershop.serveCustomer();
        }
        System.out.println("Барбер ушел домой");
    }
}
