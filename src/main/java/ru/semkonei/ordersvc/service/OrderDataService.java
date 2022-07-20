package ru.semkonei.ordersvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.semkonei.ordersvc.model.Merch;
import ru.semkonei.ordersvc.model.Order;
import ru.semkonei.ordersvc.model.OrderMerch;
import ru.semkonei.ordersvc.util.SecurityUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class OrderDataService {

    private OrderService orderService;
    private MerchService merchService;
    private OrderMerchService orderMerchService;

    @Autowired
    public OrderDataService(OrderService orderService, MerchService merchService, OrderMerchService orderMerchService) {
        this.orderService = orderService;
        this.merchService = merchService;
        this.orderMerchService = orderMerchService;
    }

    @Transactional
    public Order create(Integer merchId, Integer count, Integer userId) {
        Merch addedMerch = merchService.get(merchId);
        Order order = orderService.getInProcess(SecurityUtil.authUserId());
        if (order == null) {
            order = orderService.create(new Order(null, LocalDateTime.now(), null), SecurityUtil.authUserId());
        }
        orderMerchService.create(new OrderMerch(null, null, addedMerch, addedMerch.getCurrentPrice(), count),
                order.getId(),
                userId);
        return order;
    }

}
