package ru.semkonei.ordersvc.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Entity
public class OrderMerch extends BaseEntity{

    private Merch merch;

    private Order order;

    private Float price;

    private int count;

    public OrderMerch() {
    }

    public OrderMerch(OrderMerch orderMerch) {
        this(orderMerch.id, orderMerch.order, orderMerch.merch, orderMerch.price, orderMerch.count);
    }

    public OrderMerch(Integer id, Order order, Merch merch, Float price, int count) {
        super(id);
        this.order = order;
        this.merch = merch;
        this.price = price;
        this.count = count;
    }

    public Merch getMerch() {
        return merch;
    }

    public void setMerch(Merch merch) {
        this.merch = merch;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(super.toString());
        sb.append(" count=").append(count);
        sb.append(" price=").append(price).append("}");
        return sb.toString();
    }
}
