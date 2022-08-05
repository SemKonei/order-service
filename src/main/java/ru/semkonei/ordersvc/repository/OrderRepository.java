package ru.semkonei.ordersvc.repository;

import ru.semkonei.ordersvc.model.Order;
import ru.semkonei.ordersvc.model.OrderStatus;

import java.util.List;

public interface OrderRepository {

    Order save(Order order, Integer userId);

    Order get(Integer id, Integer userId);

    Order getNotWithStatus(Integer id, OrderStatus status, Integer userId);

    Order getWithStatus(Integer id, OrderStatus status, Integer userId);

    Order getWithOM(Integer orderId, Integer userId);

    List<Order> getAll(Integer userId);

    Integer delete(Integer id, Integer userId);
}
