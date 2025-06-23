package ru.vsu.amm.java.Services;

import ru.vsu.amm.java.Entities.Order;
import ru.vsu.amm.java.Entities.Smartphone;
import ru.vsu.amm.java.Entities.User;
import ru.vsu.amm.java.Repositories.OrderRepository;
import ru.vsu.amm.java.Repositories.SmartphoneRepository;
import ru.vsu.amm.java.Responses.OrdersResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {
    private final OrderRepository orderRepository;
    private final SmartphoneRepository smartphoneRepository;

    public OrderService(OrderRepository orderRepository, SmartphoneRepository smartphoneRepository) {
        this.orderRepository = orderRepository;
        this.smartphoneRepository = smartphoneRepository;
    }

    public OrderService() {
        this.orderRepository = new OrderRepository();
        smartphoneRepository = new SmartphoneRepository();
    }

    public void createOrder(User user, int smartphoneId) {
        Order order = new Order(user.getUserId(), smartphoneId, LocalDateTime.now(), false);
        orderRepository.save(order);
    }

    public void deleteOrder(Long orderId) {
        orderRepository.delete(orderId);
    }

    public OrdersResponse getPaidOrders(User user) {
        List<Order> orders = orderRepository.findAllByUserAndPaid(user.getUserId(), true);
        return getOrdersResponse(orders);
    }

    public OrdersResponse getNotPaidOrders(User user) {
        List<Order> orders = orderRepository.findAllByUserAndPaid(user.getUserId(), false);
        return getOrdersResponse(orders);
    }

    public void payAllOrders(User user) {
        List<Order> paidOrders = orderRepository.findAllByUserAndPaid(user.getUserId(), false);

        for (Order order : paidOrders) {
            order.setIsPaid(true);
            order.setRegistrationDate(LocalDateTime.now());
            orderRepository.update(order);
        }
    }

    private OrdersResponse getOrdersResponse(List<Order> orders) {
        Map<Long, Smartphone> usedSmartphones = new HashMap<>();
        float total = 0;
        for (Order order : orders) {
            if (!usedSmartphones.containsKey(order.getSmartphoneId())) {
                smartphoneRepository.findById(order.getSmartphoneId()).ifPresent(smartphone -> {
                    usedSmartphones.put(smartphone.getSmartphoneId(), smartphone);
                });
            }
            total += usedSmartphones.get(order.getSmartphoneId()).getPrice();
        }

        return new OrdersResponse(orders, total, usedSmartphones);
    }
}
