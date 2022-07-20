package ru.semkonei.ordersvc.repository;

import org.springframework.data.repository.query.Param;
import ru.semkonei.ordersvc.model.Order;

import java.util.List;

public interface OrderRepository {

    Order save(Order order, Integer userId);

    Order get(Integer id, Integer userId);

    Order getInProcess(Integer userId);

    Order getWithOM(Integer orderId, Integer userId);

    List<Order> getAll(Integer userId);

    boolean delete(Integer id, Integer userId);
}
