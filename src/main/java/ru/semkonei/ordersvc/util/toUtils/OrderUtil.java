package ru.semkonei.ordersvc.util.toUtils;

import ru.semkonei.ordersvc.model.Order;
import ru.semkonei.ordersvc.model.OrderStatus;
import ru.semkonei.ordersvc.web.to.OrderRequestTO;
import ru.semkonei.ordersvc.web.to.OrderResponseTO;

import java.util.List;
import java.util.stream.Collectors;

public class OrderUtil {
    public static List<OrderResponseTO> getTos(List<Order> orders) {
        return orders.stream().map(OrderUtil::createTo).collect(Collectors.toList());
    }

    public static OrderResponseTO createTo(Order order) {
        return new OrderResponseTO(order);
    }

    public static Order updateFromTo(Order order, OrderStatus status) {
        order.setStatus(status);
        return order;
    }

    public static Order getFromTo(OrderRequestTO orderTO) {
        return new Order(null, orderTO.getOrderDate(), orderTO.getStatus(), null);
    }
}
