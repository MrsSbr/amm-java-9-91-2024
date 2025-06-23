package ru.vsu.amm.java.Responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.vsu.amm.java.Entities.Order;
import ru.vsu.amm.java.Entities.Smartphone;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Data
public class OrdersResponse {
    private List<Order> orders;
    private float cost;
    private Map<Long, Smartphone> usedSmartphones;
}