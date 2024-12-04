package ru.vsu.amm.java;

import ru.vsu.amm.java.service.Barber;
import ru.vsu.amm.java.service.Barbershop;
import ru.vsu.amm.java.service.Customer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Main {
    public static void main(String[] args) {
        Barbershop barbershop = new Barbershop();
        Barber barber = new Barber();
        barbershop.hireBarber(barber);
        new Thread(barber).start();
        int clientsCount = 10;
        try(ExecutorService executorService = Executors.newFixedThreadPool(clientsCount)) {
            for (int i = 0; i < 10; i++) {
                Customer customer = new Customer();
                customer.selectBarbershop(barbershop);
                executorService.submit(customer);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(clientsCount, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        barbershop.close();
    }
}