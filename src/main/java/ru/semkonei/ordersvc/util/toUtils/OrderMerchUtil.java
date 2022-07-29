package ru.semkonei.ordersvc.util.toUtils;

import ru.semkonei.ordersvc.model.OrderMerch;
import ru.semkonei.ordersvc.web.to.*;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMerchUtil {
    public static List<OrderMerchResponseTO> getTos(List<OrderMerch> orders) {
        return orders.stream().map(OrderMerchUtil::createTo).collect(Collectors.toList());
    }

    public static OrderMerchResponseTO createTo(OrderMerch orderMerch) {
        return new OrderMerchResponseTO(orderMerch);
    }
    public static OrderMerch updateFromTo(OrderMerch orderMerch, OrderMerchRequestTO orderMerchTO) {
        orderMerch.setCount(orderMerchTO.getCount());
        orderMerch.setPrice(orderMerchTO.getPrice());
        return orderMerch;
    }

    public static OrderMerch getFromTo(OrderMerchRequestTO orderMerchTO) {
        return new OrderMerch(null, null, null, orderMerchTO.getPrice(), orderMerchTO.getCount());
    }
}
