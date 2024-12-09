package ru.vsu.amm.java;

import ru.vsu.amm.java.service.Barber;
import ru.vsu.amm.java.service.Barbershop;
import ru.vsu.amm.java.service.Customer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        Barbershop barbershop = new Barbershop();
        Barber barber = new Barber();
        barbershop.hireBarber(barber);
        try (ExecutorService barberExecutor = Executors.newSingleThreadExecutor()) {
            barberExecutor.submit(barber);
            int clientsCount = 10;
            try (ExecutorService executorService = Executors.newFixedThreadPool(clientsCount)) {
                for (int i = 0; i < clientsCount; i++) {
                    Customer customer = new Customer();
                    customer.selectBarbershop(barbershop);
                    executorService.submit(customer);
                    Thread.sleep(1200);
                }
            }
            Thread.sleep(15000);
            barber.goHome();
        }
    }
}