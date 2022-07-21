package ru.semkonei.ordersvc.util.toUtils;

import ru.semkonei.ordersvc.model.Order;
import ru.semkonei.ordersvc.to.OrderTO;

import java.util.List;
import java.util.stream.Collectors;

public class OrderUtil {
    public static List<OrderTO> getTos(List<Order> orders) {
        return orders.stream().map(OrderUtil::createTo).collect(Collectors.toList());
    }

    public static OrderTO createTo(Order order) {
        return new OrderTO(order.getId(), order.getOrderDate(), order.getStatus());
    }

    public static Order getFromTo(OrderTO orderTO) {
        return new Order(orderTO.getId(), orderTO.getOrderDate(), orderTO.getStatus(), null);
    }
}
