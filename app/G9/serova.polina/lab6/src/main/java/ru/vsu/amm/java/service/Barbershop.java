package ru.vsu.amm.java.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Barbershop {
    private final static int MAX_CUSTOMERS = 5;
    private final BlockingQueue<Customer> waitingRoom;
    private final Lock lock;
    private final Condition barberSleeping;
    private Barber barber;

    public Barbershop() {
        waitingRoom = new LinkedBlockingQueue<>(MAX_CUSTOMERS);
        lock = new ReentrantLock();
        barberSleeping = lock.newCondition();
        barber = null;
    }

    public void hireBarber(Barber barber) {
        this.barber = barber;
        barber.startWorkingAt(this);
    }

    public boolean enterWaitingRoom(Customer customer) {
        boolean added = waitingRoom.offer(customer);
        if (added) {
            lock.lock();
            try {
                if (!barber.isWorking) {
                    System.out.println("Клиент с id: " + customer.getId() + " разбудил барбера");
                    barberSleeping.signal();
                } else {
                    System.out.println("Клиент с id: " + customer.getId() + " сел в приемную");
                }
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("Клиент с id: " + customer.getId() + " ушел, в приемной нет мест");
        }
        return added;
    }

    public void serveCustomer() {
        try {
            Customer customer;
            lock.lock();

            try {
                while (waitingRoom.isEmpty()) {
                    System.out.println("Барбер спит, клиентов нет");
                    barberSleeping.await();
                }
                customer = waitingRoom.poll();
            } finally {
                lock.unlock();
            }

            System.out.println("Барбер начал стричь клиента с id: " + customer.getId());
            barber.isWorking = true;

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println("Барбер закончил стричь клиента с id: " + customer.getId());
            barber.isWorking = false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
