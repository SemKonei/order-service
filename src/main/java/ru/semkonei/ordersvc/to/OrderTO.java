package ru.semkonei.ordersvc.to;

import ru.semkonei.ordersvc.model.Order;
import ru.semkonei.ordersvc.model.OrderMerch;
import ru.semkonei.ordersvc.model.User;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.List;

public class OrderTO extends BaseTO {

    private LocalDateTime orderDate;

    private boolean completed;

    public OrderTO() {
    }

    @ConstructorProperties({"id", "orderDate", "isCompleted"})
    public OrderTO(Integer id, LocalDateTime orderDate, boolean completed) {
        super(id);
        this.orderDate = orderDate;
        this.completed = completed;
    }
    public OrderTO(Order order) {
        super(order.getId());
        this.orderDate = order.getOrderDate();
        this.completed = order.isCompleted();
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
