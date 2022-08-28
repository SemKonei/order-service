package ru.semkonei.ordersvc.web.to;

import lombok.Getter;
import lombok.Setter;
import ru.semkonei.ordersvc.model.OrderMerch;

@Getter
@Setter
public class OrderMerchRequestTOTest extends OrderMerchRequestTO {

    public OrderMerchRequestTOTest(OrderMerch orderMerch) {
        this.merchId = orderMerch.getMerch() == null ? null : orderMerch.getMerch().getId();
        this.orderId = orderMerch.getOrder() == null ? null : orderMerch.getOrder().getId();
       this.price = orderMerch.getPrice();
        this.count = orderMerch.getCount();
    }

    public OrderMerchRequestTOTest(Integer merchId, Integer orderId, Float price, Integer count) {
        this.merchId = merchId;
        this.orderId = orderId;
        this.price = price;
        this.count = count;
    }
}
