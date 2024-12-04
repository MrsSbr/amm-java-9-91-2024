package ru.vsu.amm.java.service;

import java.util.LinkedList;
import java.util.Queue;


public class Barbershop {
    private final static int MAX_CUSTOMERS = 5;
    private final Queue<Customer> waitingRoom;
    private Barber barber;
    private boolean isClosed;

    public Barbershop() {
        waitingRoom = new LinkedList<>();
        barber = null;
        isClosed = false;
    }

    public void hireBarber(Barber barber) {
        this.barber = barber;
        barber.startWorkingAt(this);
    }

    public synchronized boolean enterWaitingRoom(Customer customer) {
        if (waitingRoom.size() < MAX_CUSTOMERS) {
            waitingRoom.add(customer);
            if (waitingRoom.size() == 1 && !barber.isWorking) {
                notify();
                System.out.println("Клиент с id: " + customer.getId() + " разбудил барбера");
            } else {
                System.out.println("Клиент с id: " + customer.getId() + " сел в приемную");
            }
            return true;
        }
        else {
            System.out.println("Клиент с id: " + customer.getId() + " ушел, в приемной нет мест");
            return false;
        }
    }

    public synchronized void serveCustomer() {
        while (waitingRoom.isEmpty()) {
            try {
                System.out.println("Барбер спит, клиентов нет");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Customer customer = waitingRoom.poll();
        System.out.println("Барбер начал стричь клиента с id: " + customer.getId());
        barber.isWorking = true;

        try {
            System.out.println("Барбер стрижет клиента с id: " + customer.getId());
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Барбер закончил стричь клиента с id: " + customer.getId());
        barber.isWorking = false;
    }

    public void close() {
        isClosed = true;
    }

    public boolean isClosed() {
        return isClosed;
    }
}
