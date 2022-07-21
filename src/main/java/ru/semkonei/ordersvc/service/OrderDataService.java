package ru.semkonei.ordersvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.semkonei.ordersvc.model.Merch;
import ru.semkonei.ordersvc.model.Order;
import ru.semkonei.ordersvc.model.OrderMerch;
import ru.semkonei.ordersvc.model.OrderStatus;
import ru.semkonei.ordersvc.util.SecurityUtil;

import java.time.LocalDateTime;

@Service
public class OrderDataService {

    private final OrderService orderService;
    private final MerchService merchService;
    private final OrderMerchService orderMerchService;

    @Autowired
    public OrderDataService(OrderService orderService, MerchService merchService, OrderMerchService orderMerchService) {
        this.orderService = orderService;
        this.merchService = merchService;
        this.orderMerchService = orderMerchService;
    }

    @Transactional
    public Order create(Integer merchId, Integer count, Integer orderId, Integer userId) {
        Merch addedMerch = merchService.get(merchId);
        Order order;

        if (orderId != null) {
            order = orderService.getWithStatus(orderId, OrderStatus.DRAFT, userId);
        } else {
            order = orderService.create(
                    new Order(null, LocalDateTime.now(), OrderStatus.DRAFT, null),
                    SecurityUtil.authUserId()
            );
        }

        orderMerchService.create(new OrderMerch(null, null, addedMerch, addedMerch.getCurrentPrice(), count),
                order.getId(),
                userId);
        return order;
    }

}
