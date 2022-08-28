package ru.semkonei.ordersvc.web.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.semkonei.ordersvc.model.OrderMerch;

@Getter
@Setter
@NoArgsConstructor
public class OrderMerchResponseTO extends BaseTO {

    protected Integer merchId;

    protected Integer orderId;

    protected Float price;

    protected int count;

    public OrderMerchResponseTO(OrderMerch orderMerch) {
        super(orderMerch.getId());
        this.merchId = orderMerch.getMerch().getId();
        this.orderId = orderMerch.getOrder().getId();
        this.price = orderMerch.getPrice();
        this.count = orderMerch.getCount();
    }

    public OrderMerchResponseTO(Integer id, Integer merchId, Integer orderId, Float price, int count) {
        super(id);
        this.merchId = merchId;
        this.orderId = orderId;
        this.price = price;
        this.count = count;
    }
}
