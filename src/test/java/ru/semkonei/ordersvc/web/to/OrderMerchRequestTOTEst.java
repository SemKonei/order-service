package ru.semkonei.ordersvc.web.to;

import ru.semkonei.ordersvc.model.OrderMerch;

public class OrderMerchRequestTOTEst extends OrderMerchRequestTO {
    public OrderMerchRequestTOTEst(OrderMerch orderMerch) {
        this.merchId = orderMerch.getMerch().getId();
        this.orderId = orderMerch.getOrder().getId();
        this.price = orderMerch.getPrice();
        this.count = orderMerch.getCount();
    }
}
