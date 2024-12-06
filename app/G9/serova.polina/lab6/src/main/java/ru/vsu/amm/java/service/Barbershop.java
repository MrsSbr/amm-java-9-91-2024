package ru.vsu.amm.java.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Barbershop {
    private final static int MAX_CUSTOMERS = 5;
    private final BlockingQueue<Customer> waitingRoom;
    volatile private Barber barber;

    public Barbershop() {
        waitingRoom = new LinkedBlockingQueue<>(MAX_CUSTOMERS);
        barber = null;
    }

    public void hireBarber(Barber barber) {
        this.barber = barber;
        barber.startWorkingAt(this);
    }

    public boolean enterWaitingRoom(Customer customer) {
        boolean added = waitingRoom.offer(customer);
        if (added) {
            if (!barber.isWorking) {
                System.out.println("Клиент с id: " + customer.getId() + " разбудил барбера");
                barber.notify();
            } else {
                System.out.println("Клиент с id: " + customer.getId() + " сел в приемную");
            }
        } else {
            System.out.println("Клиент с id: " + customer.getId() + " ушел, в приемной нет мест");
        }
        return added;
    }

    public void serveCustomer() throws InterruptedException {
        if (waitingRoom.isEmpty()) {
            System.out.println("Барбер спит, клиентов нет");
        }
        Customer customer = waitingRoom.take();

        System.out.println("Барбер начал стричь клиента с id: " + customer.getId());
        barber.isWorking = true;

        Thread.sleep(3000);

        System.out.println("Барбер закончил стричь клиента с id: " + customer.getId());
        barber.isWorking = false;
    }
}
