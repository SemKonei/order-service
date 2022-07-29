package ru.semkonei.ordersvc.web.to;

import lombok.Getter;
import lombok.Setter;
import ru.semkonei.ordersvc.model.Order;
import ru.semkonei.ordersvc.model.OrderStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderResponseTO extends BaseTO{

    private LocalDateTime orderDate;

    private OrderStatus status;

    public OrderResponseTO(Order order) {
        super(order.getId());
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus();
    }
}
