package ru.semkonei.ordersvc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order extends BaseEntity {

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonIgnore
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private List<OrderMerch> merchList;

    public Order(Integer id) {
        super(id);
    }

    public Order(Order order) {
        this(order.id, order.orderDate, order.status, order.user);
    }

    public Order(Integer id, LocalDateTime orderDate, User user) {
        super(id);
        this.orderDate = orderDate;
        this.status = OrderStatus.DRAFT;
        this.user = user;
    }

    public Order(Integer id, LocalDateTime orderDate, OrderStatus status, User user) {
        super(id);
        this.orderDate = orderDate;
        this.status = status;
        this.user = user;
    }

    public boolean isDrafted() {
        return status.equals(OrderStatus.DRAFT);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(super.toString());
        sb.append(", orderDate=").append(orderDate);
        sb.append(", status=").append(status).append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return (orderDate != null && orderDate.equals(order.orderDate))
                && (status != null && status == order.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), orderDate, status);
    }
}
