package ru.semkonei.ordersvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.semkonei.ordersvc.model.Merch;
import ru.semkonei.ordersvc.model.Order;
import ru.semkonei.ordersvc.model.OrderMerch;
import ru.semkonei.ordersvc.model.OrderStatus;
import ru.semkonei.ordersvc.util.SecurityUtil;
import ru.semkonei.ordersvc.util.toUtils.OrderMerchUtil;
import ru.semkonei.ordersvc.web.to.OrderMerchRequestTO;
import ru.semkonei.ordersvc.web.to.OrderMerchResponseTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public List<OrderMerch> saveAllToNewOrder(List<OrderMerchRequestTO> orderMerchTO, Integer userId) {
        List<Merch> addedMerch = merchService.getAllInList(orderMerchTO.stream().map(OrderMerchRequestTO::getMerchId).toList());

        Order order = orderService.create(
                new Order(null, LocalDateTime.now(), OrderStatus.DRAFT, null),
                userId);

        List<OrderMerch> orderItems = new ArrayList<>();
        for (Merch merch : addedMerch) {
            for (OrderMerchRequestTO omTo : orderMerchTO) {
                if (omTo.getMerchId().equals(merch.getId())) {
                    OrderMerch om = new OrderMerch(null, null, null, merch.getCurrentPrice(), omTo.getCount());
                    om.setMerch(merch);
                    om.setOrder(order);
                    orderItems.add(om);
                }
            }
        }
        return orderMerchService.createAll(orderItems, userId);
    }

    @Transactional
    public OrderMerch saveToNewOrder(OrderMerchRequestTO orderMerchTO, Integer userId) {
        Merch addedMerch = merchService.get(orderMerchTO.getMerchId());

        Order order;
        if (orderMerchTO.getOrderId() != null) {
            order = orderService.getWithStatus(orderMerchTO.getOrderId(), OrderStatus.DRAFT, userId);
        } else {
            order = orderService.create(
                    new Order(null, LocalDateTime.now(), OrderStatus.DRAFT, null),
                    userId);
        }

        OrderMerch orderMerch = new OrderMerch(null, null, null, addedMerch.getCurrentPrice(), orderMerchTO.getCount());
        return orderMerchService.create(orderMerch, addedMerch, order, userId);
    }

    @Transactional
    public void deleteOrderItem(Integer orderMerchId, Integer orderId, Integer userId) {
        Order order = orderService.getWithStatus(orderId, OrderStatus.DRAFT, userId);
        orderMerchService.delete(orderMerchId, order.getId(), userId);
    }

    @Transactional
    public OrderMerch updateOrderItem(OrderMerchRequestTO orderMerchTO, Integer orderMerchId, Integer userId) {
        Order order = orderService.getWithStatus(orderMerchTO.getOrderId(), OrderStatus.DRAFT, userId);
        OrderMerch orderMerch = orderMerchService.get(orderMerchId, order.getId(), userId);
        return orderMerchService.update(OrderMerchUtil.updateFromTo(orderMerch, orderMerchTO), userId);
    }

    @Transactional
    public List<OrderMerch> updateOrderItemList(List<OrderMerchResponseTO> orderMerchTO, Integer orderId, Integer userId) {
        Order order = orderService.getWithStatus(orderId, OrderStatus.DRAFT, userId);
        List<OrderMerch> orderMerch = new ArrayList<>();
        orderMerchTO.forEach(
                omTO->orderMerch.add(
                        OrderMerchUtil.updateFromTo(
                                orderMerchService.get(omTO.getId(), order.getId(), userId), omTO)));
        return orderMerchService.updateList(orderMerch, userId);
    }
}
