package ru.vsu.amm.java.service;


public class Customer implements Runnable {

    private static int idCounter;
    private final int id;
    private Barbershop barbershop;

    public Customer() {
        this.id = idCounter++;
    }

    static {
        idCounter = 0;
    }

    public int getId() {
        return this.id;
    }

    public void selectBarbershop(Barbershop barbershop) {
        this.barbershop = barbershop;
    }

    @Override
    public void run() {
        if (barbershop.enterWaitingRoom(this)) {
            try {
                Thread.sleep(500);
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
