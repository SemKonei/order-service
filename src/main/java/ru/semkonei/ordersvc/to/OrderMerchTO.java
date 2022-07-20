package ru.semkonei.ordersvc.to;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.semkonei.ordersvc.model.Merch;
import ru.semkonei.ordersvc.model.Order;
import ru.semkonei.ordersvc.model.OrderMerch;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.beans.ConstructorProperties;
import java.util.Objects;

public class OrderMerchTO extends BaseTO {
    private Integer merchId;

    private Integer orderId;

    private Float price;

    private int count;

    public OrderMerchTO() {
    }

    @ConstructorProperties({"id", "merchId", "orderId", "price", "count"})
    public OrderMerchTO(Integer id, Integer merchId, Integer orderId, Float price, int count) {
        super(id);
        this.merchId = merchId;
        this.orderId = orderId;
        this.price = price;
        this.count = count;
    }
    public OrderMerchTO(OrderMerch orderMerch) {
        super(orderMerch.getId());
        this.merchId = orderMerch.getMerch().getId();
        this.orderId = orderMerch.getOrder().getId();
        this.price = orderMerch.getPrice();
        this.count = orderMerch.getCount();
    }

    public Integer getMerchId() {
        return merchId;
    }

    public void setMerchId(Integer merchId) {
        this.merchId = merchId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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
        final StringBuffer sb = new StringBuffer("OrderMerchTO{");
        sb.append("id=").append(id);
        sb.append(", merchId=").append(merchId);
        sb.append(", orderId=").append(orderId);
        sb.append(", price=").append(price);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
