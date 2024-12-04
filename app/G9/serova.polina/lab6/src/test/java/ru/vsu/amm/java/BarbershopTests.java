package ru.vsu.amm.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.service.Barber;
import ru.vsu.amm.java.service.Barbershop;
import ru.vsu.amm.java.service.Customer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class BarbershopTests {

    private Barbershop barbershop;
    private Barber barber;

    @BeforeEach
    public void setUp() {
        barbershop = new Barbershop();
        barber = new Barber();
        barbershop.hireBarber(barber);
    }

    @Test
    void testCustomerEntersEmptyRoom() {
        Customer customer = new Customer();
        customer.selectBarbershop(barbershop);

        assertTrue(barbershop.enterWaitingRoom(customer));
    }

    @Test
    void testCustomerEntersFullRoom() {
        for (int i = 0; i < 5; i++) {
            Customer customer = new Customer();
            customer.selectBarbershop(barbershop);
            barbershop.enterWaitingRoom(customer);
        }

        Customer customer = new Customer();
        assertFalse(barbershop.enterWaitingRoom(customer));
    }

    @Test
    void testBarberServing() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Customer customer = new Customer();
        customer.selectBarbershop(barbershop);
        executorService.submit(barber);
        executorService.submit(customer);

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        assertFalse(barber.isWorking);
    }

    @Test
    void testBarbershopCloses() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Customer customer = new Customer();
        customer.selectBarbershop(barbershop);
        executorService.submit(barber);
        executorService.submit(customer);

        Thread.sleep(1000);
        barbershop.close();

        assertTrue(barbershop.isClosed());

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        assertFalse(barber.isWorking);
    }
}
