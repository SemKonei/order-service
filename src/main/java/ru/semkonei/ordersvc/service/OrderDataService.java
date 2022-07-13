package ru.semkonei.ordersvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.semkonei.ordersvc.model.Merch;
import ru.semkonei.ordersvc.model.Order;
import ru.semkonei.ordersvc.model.OrderMerch;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class OrderDataService {

    private OrderService orderService;
    private OrderMerchService orderMerchService;

    @Autowired
    public OrderDataService(OrderService orderService, OrderMerchService orderMerchService) {
        this.orderService = orderService;
        this.orderMerchService = orderMerchService;
    }
/*
    @Transactional
    public Order create(List<OrderMerch> orderMerchList, Integer userId) {
        Order createdOrder = orderService.create(new Order(null, LocalDateTime.now(), null), userId);
        orderMerchList.forEach(orderMerch -> {
            orderMerch.setOrder(createdOrder);
            orderMerchService.create(orderMerch);
        });
        return createdOrder;
    }*/

    @Transactional
    public Order create(Map<Merch, Integer> merchList, Integer userId) {
        Order createdOrder = orderService.create(new Order(null, LocalDateTime.now(), null), userId);
        merchList.forEach((merch, count) ->
                orderMerchService.create(new OrderMerch(null, createdOrder, merch, merch.getCurrentPrice(), count)));
        return createdOrder;
    }

}
