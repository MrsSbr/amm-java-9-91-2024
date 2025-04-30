package ru.vsu.amm.java.unit_tests;

import org.junit.Test;
import ru.vsu.amm.java.entities.Customer;
import ru.vsu.amm.java.repository.CustomerRepository;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.*;

public class TestCustomerRepository {

    @Test
    public void testFindByName() throws SQLException {
        CustomerRepository repo = new CustomerRepository();
        Optional<Customer> customer = repo.findByName("testuser");
        assertFalse(customer.isPresent());
    }

    @Test
    public void testSave() throws SQLException {
        CustomerRepository repo = new CustomerRepository();
        Customer c = new Customer(null,"testuser","password");
        repo.save(c);
        Optional<Customer> customer = repo.findByName("testuser");
        assertTrue(customer.isPresent());
    }


}
