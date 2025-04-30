import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.config.DbConfig;
import ru.vsu.amm.java.entities.Customer;
import ru.vsu.amm.java.entities.Order;
import ru.vsu.amm.java.entities.Toy;
import ru.vsu.amm.java.repository.CustomerRepository;
import ru.vsu.amm.java.repository.OrderRepository;
import ru.vsu.amm.java.repository.ToyRepository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestOrderRepositoryIntegration {

    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private ToyRepository toyRepository;
    private DataSource dataSource;

    private Customer testCustomer;
    private Toy testToy;

    @BeforeEach
    void setup() throws SQLException {
        orderRepository = new OrderRepository();
        customerRepository = new CustomerRepository();
        toyRepository = new ToyRepository();
        dataSource = DbConfig.getDataSourceForTest();

        orderRepository.setDataSource(dataSource);
        customerRepository.setDataSource(dataSource);
        toyRepository.setDataSource(dataSource);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO customer (name, password) VALUES ('testcustomer', 'password')");
            statement.execute("INSERT INTO toy (name, price) VALUES ('testtoy', 10.00)");

            Optional<Customer> customer = customerRepository.findByName("testcustomer");
            assertTrue(customer.isPresent());
            testCustomer = customer.get();

            Optional<Toy> toy = toyRepository.findByName("testtoy");
            assertTrue(toy.isPresent());
            testToy = toy.get();
        }
    }

    @AfterEach
    void clean() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM orders");
            statement.execute("DELETE FROM customer");
            statement.execute("DELETE FROM toy");
        }
    }

    @Test
    void testFindByCustomerId() throws SQLException {
        Order order1 = new Order(null, testCustomer.getId(), testToy.getId(), 2, BigDecimal.valueOf(20.00));
        orderRepository.save(order1);
        Order order2 = new Order(null, testCustomer.getId(), testToy.getId(), 1, BigDecimal.valueOf(10.00));
        orderRepository.save(order2);

        List<Order> orders = orderRepository.findByCustomerId(testCustomer.getId());

        assertEquals(2, orders.size());
        assertEquals(testCustomer.getId(), orders.get(0).getCustomerId());
    }

    @Test
    void testSave() throws SQLException {
        Order order = new Order(null, testCustomer.getId(), testToy.getId(), 3, BigDecimal.valueOf(30.00));
        orderRepository.save(order);

        List<Order> orders = orderRepository.findByCustomerId(testCustomer.getId());

        assertEquals(1, orders.size());
        Order savedOrder = orders.get(0);
        assertEquals(testCustomer.getId(), savedOrder.getCustomerId());
        assertEquals(testToy.getId(), savedOrder.getToyId());
        assertEquals(3, savedOrder.getQuantity());
        assertEquals(BigDecimal.valueOf(30.00), savedOrder.getTotalPrice());
    }
}
