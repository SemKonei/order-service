package ru.semkonei.ordersvc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime orderDate;

    @Column(name = "status", nullable = false)
    @NotNull
    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonIgnore
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private List<OrderMerch> merchList;

    public Order() {
    }

    public Order(Order order) {
        this(order.id, order.orderDate, order.completed, order.user);
    }

    public Order(Integer id, LocalDateTime orderDate, User user) {
        super(id);
        this.orderDate = orderDate;
        this.completed = false;
        this.user = user;
    }

    public Order(Integer id, LocalDateTime orderDate, boolean completed, User user) {
        super(id);
        this.orderDate = orderDate;
        this.completed = completed;
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean status) {
        this.completed = status;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(super.toString());
        sb.append(", orderDate=").append(orderDate).append("}");
        return sb.toString();
    }
}
