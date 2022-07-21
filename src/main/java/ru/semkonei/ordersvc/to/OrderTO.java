package ru.semkonei.ordersvc.to;

import ru.semkonei.ordersvc.model.Order;
import ru.semkonei.ordersvc.model.OrderStatus;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

public class OrderTO extends BaseTO {

    private LocalDateTime orderDate;

    private OrderStatus status;

    public OrderTO() {
    }

    @ConstructorProperties({"id", "orderDate", "isCompleted"})
    public OrderTO(Integer id, LocalDateTime orderDate, OrderStatus status) {
        super(id);
        this.orderDate = orderDate;
        this.status = status;
    }
    public OrderTO(Order order) {
        super(order.getId());
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus();
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
