package ru.semkonei.ordersvc.repository;

import ru.semkonei.ordersvc.model.Order;

import java.util.List;

public interface OrderRepository {

    Order save(Order order, Integer userId);

    Order get(Integer id, Integer userId);

    List<Order> getAll(Integer userId);

    boolean delete(Integer id, Integer userId);
}
