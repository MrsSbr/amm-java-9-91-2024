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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        dataSource = DbConfig.getDataSourceForTest();
        customerRepository = new CustomerRepository();
        customerRepository.setDataSource(dataSource);

        toyRepository = new ToyRepository();
        toyRepository.setDataSource(dataSource);

        orderRepository = new OrderRepository();
        orderRepository.setDataSource(dataSource);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM orders");
            statement.execute("DELETE FROM customer");
            statement.execute("DELETE FROM toy");
        }

        Customer customer = new Customer(null, "testcustomer", "password");
        customerRepository.save(customer); // Используем метод save репозитория

        Toy toy = new Toy(null, "testtoy", BigDecimal.valueOf(10.00));
        toyRepository.save(toy);

        Optional<Customer> customerOpt = customerRepository.findByName("testcustomer");
        assertTrue(customerOpt.isPresent());
        testCustomer = customerOpt.get();

        Optional<Toy> toyOpt = toyRepository.findByName("testtoy");
        assertTrue(toyOpt.isPresent());
        testToy = toyOpt.get();
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
        assertTrue(orders.stream().allMatch(order -> order.getCustomerId().equals(testCustomer.getId())));
    }

    @Test
    void testFindByCustomerId_NoOrders() throws SQLException {
        List<Order> orders = orderRepository.findByCustomerId(testCustomer.getId());
        assertTrue(orders.isEmpty(), "Список заказов не пуст, хотя не должно быть ни одного заказа.");
    }

}
