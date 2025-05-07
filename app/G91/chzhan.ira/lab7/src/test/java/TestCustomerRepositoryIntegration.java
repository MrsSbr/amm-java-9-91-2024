import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.config.DbConfig;
import ru.vsu.amm.java.entities.Customer;
import ru.vsu.amm.java.repository.CustomerRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestCustomerRepositoryIntegration {

    private CustomerRepository customerRepository;
    private DataSource dataSource;

    @BeforeEach
    void setup() throws SQLException {
        dataSource = DbConfig.getDataSourceForTest();
        customerRepository = new CustomerRepository();
        customerRepository.setDataSource(dataSource);


        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO customer (name, password) VALUES ('testuser', 'password')");
        }
    }

    @AfterEach
    void clean() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM customer");
        }
    }

    @Test
    void testSave() throws SQLException {
        Customer newCustomer = new Customer(null, "newuser", "newpassword");
        customerRepository.save(newCustomer);

        Optional<Customer> savedCustomer = customerRepository.findByName("newuser");
        assertTrue(savedCustomer.isPresent());
        assertEquals("newuser", savedCustomer.get().getName());
    }

    @Test
    void testFindByNameNotFound() throws SQLException {
        Optional<Customer> customer = customerRepository.findByName("nonexistentuser");
        assertFalse(customer.isPresent());
    }
}
