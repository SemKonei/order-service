package ru.semkonei.ordersvc.repository;

import ru.semkonei.ordersvc.model.OrderMerch;

import java.util.List;

public interface OrderMerchRepository {

    OrderMerch save(OrderMerch orderMerch, Integer userId);

    List<OrderMerch>  saveAll(List<OrderMerch>  orderMerchList, Integer userId);

    OrderMerch get(Integer id, Integer orderId, Integer userId);

    List<OrderMerch> getAll(Integer orderId, Integer userId);

    Integer delete(Integer id, Integer orderId, Integer userId);
}
