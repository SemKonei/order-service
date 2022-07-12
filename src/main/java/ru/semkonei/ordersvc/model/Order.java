package ru.semkonei.ordersvc.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Order extends BaseEntity {

    private LocalDateTime orderDate;

    private User user;

    public Order() {
    }

    public Order(Order order) {
        this(order.id, order.orderDate, order.user);
    }

    public Order(Integer id, LocalDateTime orderDate, User user) {
        super(id);
        this.orderDate = orderDate;
        this.user = user;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(super.toString());
        sb.append(", orderDate=").append(orderDate).append("}");
        return sb.toString();
    }
}
