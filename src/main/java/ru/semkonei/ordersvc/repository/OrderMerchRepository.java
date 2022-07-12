package ru.semkonei.ordersvc.repository;

import ru.semkonei.ordersvc.model.OrderMerch;

import java.util.List;

public interface OrderMerchRepository {

    OrderMerch save(OrderMerch orderMerch);

    OrderMerch get(Integer id);

    List<OrderMerch> getAll(Integer orderId);

    boolean delete(Integer id);
}
