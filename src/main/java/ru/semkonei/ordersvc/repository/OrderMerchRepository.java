package ru.semkonei.ordersvc.repository;

import ru.semkonei.ordersvc.model.OrderMerch;

import java.util.List;

public interface OrderMerchRepository {

    OrderMerch save(OrderMerch orderMerch, Integer orderId, Integer userId);

    OrderMerch get(Integer id, Integer userId);

    List<OrderMerch> getAll(Integer orderId, Integer userId);
    List<OrderMerch> getAllForAll();

    boolean delete(Integer id, Integer userId);
}
