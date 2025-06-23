import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.vsu.amm.java.Entities.Order;
import ru.vsu.amm.java.Entities.Smartphone;
import ru.vsu.amm.java.Entities.User;
import ru.vsu.amm.java.Repositories.OrderRepository;
import ru.vsu.amm.java.Repositories.SmartphoneRepository;
import ru.vsu.amm.java.Responses.OrdersResponse;
import ru.vsu.amm.java.Services.OrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    private OrderRepository orderRepository;
    private SmartphoneRepository smartphoneRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        smartphoneRepository = mock(SmartphoneRepository.class);
        orderService = new OrderService(orderRepository, smartphoneRepository);
    }

    @Test
    void testCreateOrder() {
        User user = new User();
        user.setUserId(1L);
        int smartphoneId = 100;

        orderService.createOrder(user, smartphoneId);

        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(captor.capture());;

        Order saved = captor.getValue();
        assertEquals(1L, saved.getUserId());
        assertEquals(smartphoneId, saved.getSmartphoneId());
        assertFalse(saved.getIsPaid());
        assertNotNull(saved.getRegistrationDate());
    }

    @Test
    void testGetPaidOrders() {
        User user = new User();
        user.setUserId(2L);

        Order order = new Order(2L, 10L, LocalDateTime.now(), true);
        List<Order> orders = List.of(order);

        Smartphone smartphone = new Smartphone();
        smartphone.setSmartphoneId(10L);
        smartphone.setPrice(499.99f);

        when(orderRepository.findAllByUserAndPaid(2L, true)).thenReturn(orders);
        when(smartphoneRepository.findById(10L)).thenReturn(Optional.of(smartphone));

        OrdersResponse response = orderService.getPaidOrders(user);

        assertEquals(1, response.getOrders().size());
        assertEquals(499.99f, response.getCost(), 0.01);
        assertTrue(response.getUsedSmartphones().containsKey(10L));
    }

    @Test
    void testPayAllOrders() {
        User user = new User();
        user.setUserId(3L);

        Order order1 = new Order(3L, 1L, LocalDateTime.now().minusDays(1), false);
        Order order2 = new Order(3L, 2L, LocalDateTime.now().minusDays(2), false);

        List<Order> unpaidOrders = List.of(order1, order2);

        when(orderRepository.findAllByUserAndPaid(3L, false)).thenReturn(unpaidOrders);

        orderService.payAllOrders(user);

        assertTrue(order1.getIsPaid());
        assertTrue(order2.getIsPaid());
        verify(orderRepository, times(2)).update(any(Order.class));
    }

    @Test
    void testDeleteOrder() {
        orderService.deleteOrder(42L);
        verify(orderRepository).delete(42L);
    }
}
